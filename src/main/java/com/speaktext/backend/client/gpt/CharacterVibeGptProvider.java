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
            You are a professional Korean audiobook voice director specializing in ElevenLabs v3 voice synthesis.
            Your task is to add appropriate audio tags to the given dialogue text for enhanced emotional expression.

            Add ElevenLabs audio tags like [angry], [whispers], [laughs], [sighs], [sarcastic], [surprised], [sad] etc. directly into the text where appropriate.

            Rules:
            1. Return ONLY the original text with audio tags inserted
            2. Place tags at the beginning or middle of sentences where the emotion changes
            3. Use appropriate tags based on the context and emotion
            4. Keep all original text exactly as is, just add tags
            5. Do not add any explanations or descriptions

            Example:
            Input: "이런 오라질 년! 조밥도 못 먹는 년이 설렁탕은. 또 처먹고 지랄병을 하게"
            Output: "[angry]이런 오라질 년! 조밥도 못 먹는 년이 설렁탕은.[sighs] 또 처먹고 지랄병을 하게"
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
