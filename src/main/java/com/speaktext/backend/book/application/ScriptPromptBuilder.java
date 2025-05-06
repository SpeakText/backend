package com.speaktext.backend.book.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.application.dto.CharacterInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScriptPromptBuilder {

    private final ObjectMapper objectMapper;

    public String build(String chunkText, Map<String, CharacterInfoDto> characterDescriptions) {
        try {
            Map<String, String> promptCharacterMap = characterDescriptions.values().stream()
                    .collect(Collectors.toMap(
                            c -> c.name() + " - " + c.characterId(),
                            CharacterInfoDto::description
                    ));

            String jsonCharacters = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(promptCharacterMap);

            log.info("jsonCharacters: {}", jsonCharacters);

            return """
당신은 소설의 일부를 스크립트 형식으로 재구성하는 언어 분석가입니다.

아래 텍스트를 기반으로 **두 가지 결과**를 생성해주세요:

1. 각 줄이 발화문 또는 나레이션으로 구성된 스크립트  
2. 등장인물 전체 목록 (이전에 주어진 인물 포함, 변경/유지 여부 표시)

---

📌 **스크립트 작성 규칙**:

- 각 줄은 다음 형식을 따라야 합니다:
  - `character-id: "발화문"`  ← 따옴표 안의 문장은 반드시 화자의 발화입니다.
  - `나레이션 - narration: 장면 설명` ← 따옴표 없이 나레이션으로 작성하세요.
  
- 화자의 말이 아닌 설명이나 장면 묘사는 반드시 **나레이션**으로 처리해야 합니다.  
- 모든 문장은 **요약 없이**, 순서를 유지하며 원문 그대로 사용해야 합니다.  
- “나”라는 표현은 항상 **1인칭 화자**이며, 이미 지정된 `character-id`를 재사용해야 합니다. 단, 이 화자가 말한 문장인지 아닌지는 **따옴표 유무**로 판단합니다.
- 같은 인물로 판단되면 이전 등장인물의 `character-id`를 재사용하세요.  
- 임의로 `character-id`를 새로 부여하지 마세요. 반드시 매핑된 ID를 사용하세요.
-  반드시 따옴표 안에 있는 문장은 화자의 발화로 간주되며, **character-id**로 지정해야 합니다. \s
   반대로 따옴표가 없는 문장은 나레이션이며, 반드시 `나레이션 - narration:` 으로 작성합니다.                  
   예시:
- "나는 오늘 이상한 꿈을 꿨어." → character-1: "나는 오늘 이상한 꿈을 꿨어."
- 나는 오늘 이상한 꿈을 꿨다. → 나레이션 - narration: 나는 오늘 이상한 꿈을 꿨다.
                    
---

📌 **응답 형식 예시**:

[스크립트 시작]
character-1: "다녀왔어요."
character-2: "어서 와, 점순아."
나레이션 - narration: 점순은 신발을 벗고 조심스레 방으로 들어왔다.
character-1: "오늘은 기분이 좀 이상해요."
[스크립트 끝]

[등장인물 업데이트 시작]
{
  "점순 - character-1": "처음에는 '여성'으로 표현되었으나, 이름이 확인됨. 밝고 대담한 성격.",
  "엄마 - character-2": "점순의 어머니로 추정됨. 온화하고 다정한 말투."
}
[등장인물 업데이트 끝]

---

📌 **주의사항**:

- 등장인물 업데이트에는 이전 등장인물도 반드시 포함되어야 하며,  
  이름과 설명이 바뀌더라도 동일한 `character-id`를 유지해야 합니다.  
- character-id가 바뀌거나 누락되는 일이 없도록 하세요.  
- **모든 원문 문장은 빠짐없이 포함되어야 하며**, 절대 요약하거나 창작하지 마세요.  
- 텍스트 속 표현이 불명확하더라도 그대로 사용하고, 가능한 추론만 하세요.

---

📘 **이전 등장인물 목록 (JSON 형식)**:
%s

📘 **이번 파트 텍스트**:
%s
""".formatted(jsonCharacters, chunkText);

        } catch (Exception e) {
            throw new RuntimeException("프롬프트 생성 실패", e);
        }
    }

}
