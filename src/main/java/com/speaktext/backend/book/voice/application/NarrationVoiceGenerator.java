package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.VoiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
@RequiredArgsConstructor
public class NarrationVoiceGenerator {

    private final VoiceProvider voiceProvider;

    private static final String NARRATION_INSTRUCTIONS = """
        Voice Style: Natural and human-like, prioritizing smooth and realistic narration.
        Tone: Warm, friendly, yet neutral and professional. Avoid robotic or overly monotone delivery.
        Pacing: Steady and smooth, with natural rhythm. Slight emphasis on important words for clarity.
        Emotion: Subtle emotional depth to make storytelling engaging, without being exaggerated.
        Pronunciation: Clear and precise, ensuring intelligibility and listener comfort.
        Flow: Maintain a calm, confident cadence that keeps the listener immersed.
        """;

    public void generate(String identificationNumber, Long index, String speaker, String utterance, VoiceType voiceType) {
        String fileName = identificationNumber + "_" + index;

        Path path = voiceProvider.generateVoice(
                utterance,
                voiceType.toString(),
                NARRATION_INSTRUCTIONS,
                fileName
        );

    }
}