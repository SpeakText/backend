package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.VoiceType;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NarrationVoiceGenerator {

    private final VoiceProvider voiceProvider;
    private final VoiceRegisterService voiceRegisterService;

    private static final String NARRATION_INSTRUCTIONS = """
    Voice Style: Natural and human-like, prioritizing smooth and realistic narration.
    Tone: Warm and friendly, but maintain a neutral and consistent storytelling voice. Avoid emotional exaggeration.
    Pacing: Steady and smooth, with a calm and even rhythm. Emphasize clarity and flow over expressiveness.
    Emotion: Keep emotional expression minimal and restrained. Focus on delivering information and maintaining listener immersion, without dramatic inflection.
    Pronunciation: Clear and precise articulation for listener comfort and intelligibility.
    Flow: Maintain a stable and composed cadence, ensuring the narration feels professional and unobtrusive.
    """;

    public void generate(String identificationNumber, Long index, String speaker, String utterance, VoiceType voiceType) {
        String fileName = identificationNumber + "_" + index;

        Response response = voiceProvider.generateVoice(
                utterance,
                voiceType.toString(),
                NARRATION_INSTRUCTIONS,
                fileName,
                1.0
        );

        voiceRegisterService.registerVoice(identificationNumber, index, response, fileName);
    }

}
