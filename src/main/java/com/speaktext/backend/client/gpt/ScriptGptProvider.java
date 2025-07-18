package com.speaktext.backend.client.gpt;

import com.speaktext.backend.book.script.application.implement.ScriptProvider;
import com.speaktext.backend.book.script.application.implement.ScriptPromptBuilder;
import com.speaktext.backend.book.script.application.dto.CharacterDto;
import com.speaktext.backend.client.gpt.dto.ScriptGenerationRequest;
import com.speaktext.backend.client.gpt.dto.ScriptGenerationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScriptGptProvider implements ScriptProvider {

    private static final String SYSTEM_PROMPT = "당신은 소설을 스크립트 형태로 재구성하는 언어 분석가입니다.";
    private static final String MODEL_NAME = "gpt-4.1";
    private static final double TEMPERATURE = 0.1;

    private final ScriptGenerationGptClient client;
    private final ScriptPromptBuilder promptBuilder;

    @Override
    public String generateScript(String chunkText, List<CharacterDto> mainCharacters) {
        String userPrompt = promptBuilder.build(chunkText, mainCharacters);

        ScriptGenerationRequest request = ScriptGenerationRequest.builder()
                .model(MODEL_NAME)
                .temperature(TEMPERATURE)
                .messages(List.of(
                        ScriptGenerationRequest.Message.builder().role("system").content(SYSTEM_PROMPT).build(),
                        ScriptGenerationRequest.Message.builder().role("user").content(userPrompt).build()
                ))
                .build();

        ScriptGenerationResponse response = client.generate(request);
        return response.getChoices()[0].getMessage().getContent();
    }

}
