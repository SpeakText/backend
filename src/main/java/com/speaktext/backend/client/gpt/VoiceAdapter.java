package com.speaktext.backend.client.gpt;

import com.speaktext.backend.book.voice.application.VoiceProvider;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class VoiceAdapter implements VoiceProvider {

    private static final String MODEL_NAME = "gpt-4o-mini-tts";
    private static final String RESPONSE_FORMAT = "mp3";

    private final VoiceGenerationClient speechClient;

    @Override
    public Response generateVoice(String text, String voice, String instructions, String filename, double speed) {
        Map<String, Object> request = Map.of(
                "model", MODEL_NAME,
                "input", text,
                "voice", voice.toLowerCase(),
                "response_format", RESPONSE_FORMAT,
                "speed", speed,
                "instructions", instructions
        );

        return speechClient.generateSpeech(request);
    }

}
