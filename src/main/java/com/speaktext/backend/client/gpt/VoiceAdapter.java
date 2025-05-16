package com.speaktext.backend.client.gpt;

import com.speaktext.backend.book.voice.application.VoiceProvider;
import com.speaktext.backend.book.voice.domain.repository.VoiceStorage;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class VoiceAdapter implements VoiceProvider {

    private static final String MODEL_NAME = "gpt-4o-mini-tts";
    private static final String RESPONSE_FORMAT = "mp3";

    private final VoiceGenerationClient speechClient;
    private final VoiceStorage voiceStorage;

    @Override
    public Path generateVoice(String text, String voice, String instructions, String filename, double speed) {
        Map<String, Object> request = Map.of(
                "model", MODEL_NAME,
                "input", text,
                "voice", voice.toLowerCase(),
                "response_format", RESPONSE_FORMAT,
                "speed", speed,
                "instructions", instructions
        );

        Response response = speechClient.generateSpeech(request);

        try (InputStream inputStream = response.body().asInputStream()) {
            return voiceStorage.save(filename, inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate voice", e);
        }
    }

}