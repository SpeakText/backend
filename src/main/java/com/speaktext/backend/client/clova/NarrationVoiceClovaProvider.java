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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private static final String SILENCE_FILE_PATH = "silenceFile/silence.mp3";

    private final RestTemplate restTemplate;

    @Value("${llm.api.clova.base-url}")
    private String clovaUrl;

    @Value("${llm.api.clova.key}")
    private String clovaApiKey;

    @Value("${llm.api.clova.secret}")
    private String clovaApiSecret;

    private boolean isValidText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }

        String trimmedText = text.trim();

        // "..." 또는 "…" 같은 무의미한 텍스트 필터링
        if (trimmedText.equals("...") || trimmedText.equals("…") ||
            trimmedText.matches("^\\.{2,}$") || trimmedText.matches("^…+$")) {
            return false;
        }

        // 특수문자나 공백만으로 이루어진 텍스트 필터링
        if (trimmedText.matches("^[\\s\\p{Punct}]*$")) {
            return false;
        }

        return true;
    }

    private VoiceData createSilenceVoiceData(String fileName) {
        try {
            Path silencePath = Paths.get(SILENCE_FILE_PATH);
            if (!Files.exists(silencePath)) {
                log.error("무음 파일을 찾을 수 없습니다: {}", SILENCE_FILE_PATH);
                return null;
            }

            InputStream inputStream = new FileInputStream(silencePath.toFile());
            return new VoiceData(inputStream, fileName);
        } catch (IOException e) {
            log.error("무음 파일 로드 중 오류 발생: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public VoiceData generate(String identificationNumber, Long index, String speaker, String text,
                              NarrationVoiceType voice, String fileName, double speed) {

        if (!isValidText(text)) {
            log.info("나레이션 텍스트가 유효하지 않음 - 무음 파일 사용: {} (텍스트: '{}')", fileName, text);
            return createSilenceVoiceData(fileName);
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

            // "empty synthesized data" 또는 "TN result is empty" 오류면 무음 파일 반환
            if (errorMessage != null &&
                (errorMessage.contains("empty synthesized data") ||
                 errorMessage.contains("TN result is empty") ||
                 errorMessage.contains("text parameter check"))) {
                log.warn("Clova TTS에서 처리할 수 없는 텍스트 - 무음 파일 사용: {} (오류: {})", fileName, errorMessage);
                return createSilenceVoiceData(fileName);
            }

            // 기타 예외의 경우에도 무음 파일로 대체
            log.error("Clova TTS 요청 중 예외 발생 - 무음 파일 사용: {} (오류: {})", fileName, errorMessage, e);
            return createSilenceVoiceData(fileName);
        }
    }

}