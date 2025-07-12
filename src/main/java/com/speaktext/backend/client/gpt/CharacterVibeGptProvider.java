package com.speaktext.backend.client.gpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.client.gpt.dto.VibeGenerationRequest;
import com.speaktext.backend.client.gpt.dto.VibeGenerationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterVibeGptProvider {

    private final VibeGenerationGptClient vibeGenerationGptClient;

    private static final String SYSTEM_PROMPT = """
            You are a professional Korean audiobook voice director.
            Given a line of dialogue and its surrounding context, your task is to generate a high-quality voice instruction (VIBE)
            that guides the tone, delivery, and emotional nuance for Korean speech synthesis.
            Use the following 6 labeled sections in your response, and reflect the full vocal behavior in natural spoken Korean:
            
            Voice Affect: Describe the overall vocal attitude (e.g., calm, teasing, stern). This reflects the speaker’s emotional stance and energy.
            
            Tone: Describe the emotional color and speaker's intent (e.g., light-hearted, affectionate, scolding, solemn), along with formality or familiarity level.
            
            Pacing: Describe the speech speed and rhythm. Include natural Korean delivery patterns such as chunked phrasing, breath timing, or gradual slowing or speeding within the sentence.
            
            Emotion: Describe the underlying feeling and how it should flow or shift (e.g., rising irritation that softens, hidden sadness beneath formality). Capture emotional arcs common in Korean expressive delivery.
            
            Pronunciation: Describe the texture and clarity of articulation. Indicate whether the tone is sharp, soft, rough, or breathy, and whether dialect influence (e.g., Jeolla-do, Gyeongsang-do) should affect the pronunciation or melodic flow.
            
            Pauses: Identify where the speaker should pause briefly, especially where Korean sentence structure implies emotional breaks, rhetorical timing, or dialect-based hesitation. Korean typically includes brief rises before final falls — reflect that.
            
            Do not reference or quote specific words or syllables from the input sentence.
            Do not provide word-level emphasis instructions.
            
            Your goal is to provide natural, human-like voice direction suitable for expressive Korean speech — with careful attention to Korean intonation structure, emotional nuance, and regional rhythm if applicable.
        """;
    private final ObjectMapper objectMapper;

    public String generateVibe(String userPrompt) {
        VibeGenerationRequest request = VibeGenerationRequest.builder()
                .model("gpt-4.1")
                .temperature(0.7)
                .messages(List.of(
                        VibeGenerationRequest.Message.builder().role("system").content(SYSTEM_PROMPT).build(),
                        VibeGenerationRequest.Message.builder().role("user").content(userPrompt).build()
                ))
                .build();

        String rawResponse = vibeGenerationGptClient.generateVibeRaw(request);
        System.out.println("Raw GPT Response:\n" + rawResponse);

        try {
            VibeGenerationResponse response= objectMapper.readValue(rawResponse, VibeGenerationResponse.class);
            return response.getChoices()[0].getMessage().getContent();
        } catch (Exception e) {
            throw new RuntimeException("JSON 파싱 실패: " + e.getMessage(), e);
        }
        
    }

}
