package com.speaktext.backend.client.clova;

import com.speaktext.backend.book.script.domain.NarrationVoiceType;
import com.speaktext.backend.book.voice.application.NarrationVoiceProvider;
import com.speaktext.backend.book.voice.domain.VoiceData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Slf4j
@RequiredArgsConstructor
public class NarrationVoiceClovaProvider implements NarrationVoiceProvider {

    private static final int DEFAULT_VOLUME = 0;
    private static final int DEFAULT_PITCH = 0;
    private static final int DEFAULT_EMOTION = 0;
    private static final int DEFAULT_EMOTION_STRENGTH = 1;
    private static final String DEFAULT_FORMAT = "mp3";
    private static final int DEFAULT_SAMPLING_RATE = 24000;
    private static final int DEFAULT_ALPHA = 0;
    private static final int DEFAULT_END_PITCH = 0;

    private final RestTemplate restTemplate;

    @Value("${llm.api.clova.base-url}")
    private String clovaUrl;

    @Value("${llm.api.clova.key}")
    private String clovaApiKey;

    @Value("${llm.api.clova.secret}")
    private String clovaApiSecret;

    @Override
    public VoiceData generate(String identificationNumber, Long index, String speaker, String text,
                              NarrationVoiceType voice, String fileName, double speed) {

        // 텍스트가 비어있거나 공백만 있으면 null 반환 (요청하지 않음)
        if (text == null || text.trim().isEmpty()) {
            log.info("나레이션 텍스트가 비어있음 - 요청 생략: {}", fileName);
            return null;
        }

        String clovaVoice = VoiceTypeClovaMapper.mapToEngineVoiceName(voice);

        // Form 데이터 구성
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        form.add("speaker", clovaVoice);
        form.add("text", text);
        form.add("volume", DEFAULT_VOLUME);
        form.add("speed", (int) speed);
        form.add("pitch", DEFAULT_PITCH);
        form.add("emotion", DEFAULT_EMOTION);
        form.add("emotionStrength", DEFAULT_EMOTION_STRENGTH);
        form.add("format", DEFAULT_FORMAT);
        form.add("sampleRate", DEFAULT_SAMPLING_RATE);
        form.add("alpha", DEFAULT_ALPHA);
        form.add("endPitch", DEFAULT_END_PITCH);

        // Header 구성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("X-NCP-APIGW-API-KEY-ID", clovaApiKey);
        headers.set("X-NCP-APIGW-API-KEY", clovaApiSecret);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(form, headers);

        String endpoint = clovaUrl + "/tts-premium/v1/tts";

        try {
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(
                    endpoint,
                    HttpMethod.POST,
                    requestEntity,
                    byte[].class
            );

            if (responseEntity.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("❌ Clova TTS 응답 실패: " + responseEntity.getStatusCode());
            }

            byte[] body = responseEntity.getBody();
            if (body == null || body.length == 0) {
                throw new RuntimeException("❌ Clova TTS 응답 본문이 비어 있음");
            }

            InputStream inputStream = new ByteArrayInputStream(body);
            return new VoiceData(inputStream, fileName);

        } catch (Exception e) {
            String errorMessage = e.getMessage();

            // "empty synthesized data" 또는 "TN result is empty" 오류면 null 반환
            if (errorMessage != null &&
                (errorMessage.contains("empty synthesized data") ||
                 errorMessage.contains("TN result is empty") ||
                 errorMessage.contains("text parameter check"))) {
                log.warn("Clova TTS에서 처리할 수 없는 텍스트 - 요청 생략: {} (오류: {})", fileName, errorMessage);
                return null;
            }

            throw new RuntimeException("❌ Clova TTS 요청 중 예외 발생", e);
        }
    }

}