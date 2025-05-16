package com.speaktext.backend.client.gpt;

import com.speaktext.backend.client.gpt.dto.VibeGenerationRequest;
import com.speaktext.backend.client.gpt.dto.VibeGenerationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterVibeAdapter {

    private final VibeGenerationClient vibeGenerationClient;

    private static final String SYSTEM_PROMPT = """
        You are a professional voice director for audiobook characters, specialized in Korean language narration.
        Given a dialogue context and the character's current line,
        generate a VIBE instruction for Text-to-Speech synthesis.

        Your goal is to ensure the voice output sounds natural and fluent in Korean,
        avoiding any awkward literal translations from English.
        Pay special attention to tone, pacing, and emotional nuance appropriate for Korean-speaking listeners.

        Focus on:
        - Voice Affect: Character's emotional state and personality.
        - Tone: Formality level, emotional warmth, and conversational appropriateness.
        - Pacing: Adjust speech speed for clarity, natural rhythm, and expressiveness.
        - Emotions: Subtle and context-aware, not exaggerated.
        - Pronunciation: Clear articulation, avoiding robotic or overly dramatic emphasis.
        - Pauses: Insert meaningful pauses for natural flow and listener engagement.

        Example Output Format:
        Voice Affect: Calm, composed, and reassuring. Competent and in control, instilling trust.
        Tone: Sincere, empathetic, with genuine concern for the listener.
        Pacing: Steady, with slight emphasis on key points for clarity.
        Emotions: Subtle emotional depth without exaggeration.
        Pronunciation: Clear and precise, suitable for Korean listening habits.
        Pauses: Natural pauses between clauses, especially before important phrases.
        """;

    public String generateVibe(String userPrompt) {
        VibeGenerationRequest request = VibeGenerationRequest.builder()
                .model("gpt-4o")
                .temperature(0.7)
                .messages(List.of(
                        VibeGenerationRequest.Message.builder().role("system").content(SYSTEM_PROMPT).build(),
                        VibeGenerationRequest.Message.builder().role("user").content(userPrompt).build()
                ))
                .build();

        VibeGenerationResponse response = vibeGenerationClient.generateVibe(request);

        return response.getChoices()[0].getMessage().getContent();
    }

}
