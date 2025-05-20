package com.speaktext.backend.client.gpt;

import com.speaktext.backend.book.script.domain.VoiceType;
import com.speaktext.backend.book.voice.application.CharacterVibeGenerator;
import com.speaktext.backend.book.voice.application.VoiceProvider;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class VoiceGptAdapter implements VoiceProvider {

    private static final String MODEL_NAME = "gpt-4o-mini-tts";
    private static final String RESPONSE_FORMAT = "mp3";
    private static final String NARRATION_INSTRUCTIONS = """
    Voice Style: Natural and human-like, prioritizing smooth and realistic narration.
    Tone: Warm and friendly, but maintain a neutral and consistent storytelling voice. Avoid emotional exaggeration.
    Pacing: Steady and smooth, with a calm and even rhythm. Emphasize clarity and flow over expressiveness.
    Emotion: Keep emotional expression minimal and restrained. Focus on delivering information and maintaining listener immersion, without dramatic inflection.
    Pronunciation: Clear and precise articulation for listener comfort and intelligibility.
    Flow: Maintain a stable and composed cadence, ensuring the narration feels professional and unobtrusive.
    """;

    private final VoiceGenerationGptClient speechClient;
    private final CharacterVibeGenerator characterVibeGenerator;

    @Override
    public Response generateCharacterVoice(String identificationNumber, Long index, String speaker, String text, VoiceType voice, String filename, double speed) {
        String vibe = characterVibeGenerator.generateVibe(
                identificationNumber,
                index,
                speaker
        );

        Map<String, Object> request = Map.of(
                "model", MODEL_NAME,
                "input", text,
                "voice", voice.toString().toLowerCase(),
                "response_format", RESPONSE_FORMAT,
                "speed", speed,
                "instructions", vibe
        );

        return speechClient.generateSpeech(request);
    }

    @Override
    public Response generateNarrationVoice(String identificationNumber, Long index, String speaker, String text, VoiceType voice, String fileName, double speed) {
        Map<String, Object> request = Map.of(
                "model", MODEL_NAME,
                "input", text,
                "voice", voice.toString().toLowerCase(),
                "response_format", RESPONSE_FORMAT,
                "speed", speed,
                "instructions", NARRATION_INSTRUCTIONS
        );

        return speechClient.generateSpeech(request);
    }

}
