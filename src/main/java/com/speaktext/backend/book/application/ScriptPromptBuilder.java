package com.speaktext.backend.book.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ScriptPromptBuilder {

    private final ObjectMapper objectMapper;

    public String build(String chunkText, Map<String, String> characterDescriptions) {
        try {
            String jsonCharacters = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(characterDescriptions);
            return """
                    다음은 소설의 일부입니다. 이 텍스트를 발화자-발화문, 나레이션으로 구성된 스크립트 형식으로 변환해주세요.

                    요구 형식:
                    - "나레이션": "문장"
                    - "등장인물 이름 또는 유추된 호칭": "발화문"

                    규칙:
                    - 나레이션이면 하나의 문장, 발화문이면 따움표 안의 내용이 들어가야 합니다.
                    - 말하는 사람이 확실하지 않으면, 등장인물 설명을 참고해서 가장 알맞은 이름이나 호칭("엄마", "지수", "선배", "철수" 등)을 직접 사용해주세요.
                    - '나'라는 표현이 등장하면, 그 인물은 항상 주인공으로 간주해주세요. 이름이 이미 등장했다면 그 이름을 사용하고, 그렇지 않으면 '1인칭 화자' 라는 식으로 표현해주세요.
                    - 가능한 경우 '인물1' 같은 임시 이름은 사용하지 마세요.
                    - 새로운 인물이 등장하면 이름이나 호칭과 함께 설명을 추가해주세요. (예: "지수": "결단력 있는 남성, 주인공으로 추정")
                    - 등장인물 정보가 변경되지 않았다면 그대로 두세요.

                    응답 형식:
                    [스크립트 시작]
                    (스크립트 형식으로 변환된 내용)
                    [스크립트 끝]

                    [등장인물 업데이트 시작]
                    (변경되었거나 유지된 등장인물 JSON. 이름/호칭을 기준으로 작성)
                    [등장인물 업데이트 끝]

                    이전 등장인물 목록 (JSON 형식):
                    %s

                    이번 파트:
                    %s
                    """.formatted(jsonCharacters, chunkText);
        } catch (Exception e) {
            throw new RuntimeException("프롬프트 생성 실패", e);
        }
    }

}
