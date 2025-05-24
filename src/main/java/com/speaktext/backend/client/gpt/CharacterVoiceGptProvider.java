package com.speaktext.backend.client.gpt;

import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.voice.application.CharacterVibeGenerator;
import com.speaktext.backend.book.voice.application.CharacterVoiceProvider;
import com.speaktext.backend.book.voice.domain.VoiceData;
import feign.Response;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.util.Map;

@RequiredArgsConstructor
public class CharacterVoiceGptProvider implements CharacterVoiceProvider {

    private static final String MODEL_NAME = "gpt-4o-mini-tts";
    private static final String RESPONSE_FORMAT = "mp3";
    private final VoiceGenerationGptClient speechClient;
    private final CharacterVibeGenerator characterVibeGenerator;

    @Override
    public VoiceData generate(String identificationNumber, Long index, String speaker, String text, CharacterVoiceType voice, String fileName, double speed) {
        String vibe = characterVibeGenerator.generateVibe(
                identificationNumber,
                index,
                speaker
        );

        Map<String, Object> request = Map.of(
                "model", MODEL_NAME,
                "input", text,
                "voice", VoiceTypeGptMapper.mapToEngineVoiceName(voice),
                "response_format", RESPONSE_FORMAT,
                "speed", speed,
                "instructions", vibe
        );

        try {
            Response response = speechClient.generateSpeech(request);

            if (response.status() != 200) {
                String errorMessage = "[응답 오류]";
                try (InputStream errorStream = response.body() != null ? response.body().asInputStream() : null) {
                    if (errorStream != null) {
                        errorMessage = new String(errorStream.readAllBytes());
                    }
                }
                throw new RuntimeException("GPT TTS 요청 실패: " + response.status() + "\n본문: " + errorMessage);
            }

            return new VoiceData(response.body().asInputStream(), fileName);
        } catch (Exception e) {
            throw new RuntimeException("GPT 음성 생성 실패", e);
        }
    }

}
