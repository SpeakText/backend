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

    private static final String SYSTEM_PROMPT = """
            당신은 소설 텍스트를 오디오북용 스크립트로 변환하는 전문 언어 분석가입니다.

            핵심 역할:
            - 소설의 서술문에서 화자별 발화문과 나레이션을 정확히 구분
            - 등장인물의 일관성 있는 식별 및 추적
            - 문맥을 고려한 정확한 화자 매칭

            작업 원칙:
            1. 정확성: 원문의 의미와 뉘앙스를 보존
            2. 일관성: 동일 인물에 대한 ID 일관성 유지
            3. 완전성: 모든 문장을 빠짐없이 처리
            4. 문맥성: 이전 정보를 활용한 연속성 있는 해석

            당신의 분석이 고품질 오디오북 제작의 기반이 됩니다.""";
    private static final String MODEL_NAME = "gpt-4o";
    private static final double TEMPERATURE = 0.3;

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
