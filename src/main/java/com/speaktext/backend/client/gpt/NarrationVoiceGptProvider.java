package com.speaktext.backend.client.gpt;

import com.speaktext.backend.book.script.domain.NarrationVoiceType;
import com.speaktext.backend.book.voice.application.NarrationVoiceProvider;
import com.speaktext.backend.book.voice.domain.VoiceData;
import feign.Response;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class NarrationVoiceGptProvider implements NarrationVoiceProvider {

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

    @Override
    public VoiceData generate(String identificationNumber, Long index, String speaker, String text,
                              NarrationVoiceType voice, String fileName, double speed) {
        Map<String, Object> request = Map.of(
                "model", MODEL_NAME,
                "input", text,
                "voice", VoiceTypeGptMapper.mapToEngineVoiceName(voice),
                "response_format", RESPONSE_FORMAT,
                "speed", speed,
                "instructions", NARRATION_INSTRUCTIONS
        );

        try {
            Response response = speechClient.generateSpeech(request);
            return new VoiceData(response.body().asInputStream(), fileName);
        } catch (Exception e) {
            throw new RuntimeException("GPT TTS 응답 처리 실패", e);
        }
    }

}
