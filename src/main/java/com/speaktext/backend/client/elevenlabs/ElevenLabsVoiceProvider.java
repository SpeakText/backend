package com.speaktext.backend.client.elevenlabs;

import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.voice.application.CharacterVibeGenerator;
import com.speaktext.backend.book.voice.application.CharacterVoiceProvider;
import com.speaktext.backend.book.voice.domain.VoiceData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ElevenLabsVoiceProvider implements CharacterVoiceProvider {

    @Value("${llm.api.elevenlabs.model}")
    private String modelId;

    private final ElevenLabsVoiceClient elevenLabsVoiceClient;
    private final CharacterVibeGenerator characterVibeGenerator;

    @Override
    public VoiceData generate(String identificationNumber, Long index, String speaker, String text, CharacterVoiceType voice, String fileName, double speed) {
        // VIBE 생성: 실제 텍스트에 오디오 태그 추가
        String vibeText = characterVibeGenerator.generateVibe(
                identificationNumber,
                index,
                speaker
        );

        String voiceId = VoiceTypeElevenLabsMapper.mapToElevenLabsVoiceId(voice);

        // ElevenLabs v3 curl과 동일한 간단한 API 구조
        Map<String, Object> request = new HashMap<>();

        // VIBE가 있으면 VIBE 텍스트 사용, 없으면 원본 텍스트 사용
        String finalText = (vibeText != null && !vibeText.trim().isEmpty()) ? vibeText : text;
        request.put("text", finalText);
        request.put("model_id", modelId);

        // voice_settings 제거 - curl에서는 사용하지 않음

        try {
            System.out.println("요청 본문: " + request);

            ResponseEntity<byte[]> response = elevenLabsVoiceClient.generateSpeech(voiceId, request);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return new VoiceData(new ByteArrayInputStream(response.getBody()), fileName);
            } else {
                throw new RuntimeException("ElevenLabs TTS 요청 실패: " + response.getStatusCode());
            }

        } catch (HttpClientErrorException e) {
            System.out.println("HTTP 오류: " + e.getStatusCode());
            System.out.println("오류 응답 본문: " + e.getResponseBodyAsString());
            throw new RuntimeException("ElevenLabs TTS 요청 실패: " + e.getStatusCode() + "\n본문: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            System.out.println("ElevenLabs 음성 생성 예외: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("ElevenLabs 음성 생성 실패", e);
        }
    }
}