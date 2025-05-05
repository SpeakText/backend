package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.ScriptGenerationResult;
import com.speaktext.backend.client.ScriptGenerationClient;
import com.speaktext.backend.client.dto.ScriptGenerationRequest;
import com.speaktext.backend.client.dto.ScriptGenerationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class LLMGenerator {

    private final ScriptGenerationClient client;
    private final ScriptPromptBuilder promptBuilder;
    private final ScriptParser parser;

    public ScriptGenerationResult generate(String chunkText, Map<String, String> mainCharacters) {
        String systemPrompt = "당신은 소설을 스크립트 형태로 재구성하는 언어 분석가입니다.";
        String userPrompt = promptBuilder.build(chunkText, mainCharacters);

        ScriptGenerationRequest request = ScriptGenerationRequest.builder()
                .model("gpt-4o-mini")
                .temperature(0.7)
                .messages(List.of(
                        ScriptGenerationRequest.Message.builder().role("system").content(systemPrompt).build(),
                        ScriptGenerationRequest.Message.builder().role("user").content(userPrompt).build()
                ))
                .build();

        ScriptGenerationResponse response = client.generate(request);
        String content = response.getChoices()[0].getMessage().getContent();

        return parser.parse(content);
    }

}
