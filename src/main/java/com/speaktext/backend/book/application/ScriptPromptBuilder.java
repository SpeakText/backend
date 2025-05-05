package com.speaktext.backend.book.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.application.dto.CharacterInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

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

            return """
당신은 소설의 일부를 스크립트 형식으로 변환하는 언어 분석가입니다.

아래 텍스트를 분석하여 **스크립트**와 **등장인물 정보 전체 목록**을 출력하세요.

---

📌 변환 규칙 (꼭 지켜야 합니다):

1. **텍스트의 모든 문장은 반드시 하나도 빠짐없이 변환되어야 하며**,  
   어떤 문장도 생략, 요약, 병합, 재구성해서는 안 됩니다.  
   👉 한 문장은 **하나의 발화 또는 나레이션**으로 변환되어야 합니다.

   ❌ 잘못된 예:  
   "점순이는 집에 돌아왔다. 그녀는 닭을 들고 있었다." →  
   `"character-1": "점순이는 닭을 들고 집에 돌아왔다."` (❌ 병합된 문장)

   ✅ 올바른 예:  
   `"character-1": "점순이는 집에 돌아왔다."`  
   `"character-1": "그녀는 닭을 들고 있었다."`

2. 각 스크립트 라인은 다음 중 하나의 형식을 따라야 합니다:
   - `"character-id": "발화문"`
   - `나레이션 - narration: 설명문`

3. **따옴표로 감싸진 문장**은 반드시 **발화문**으로 처리하고,
   **그 외 문장**은 **나레이션**으로 간주합니다.

4. `"나"`는 항상 1인칭 화자로, 별도 이름이 없는 경우 `character-1`로 지정하세요.

5. **동일 인물 유추**: 예를 들어, "여성"과 "점순"이 같은 인물로 보이면 동일한 character-id를 사용하세요.

---

📌 응답 형식 예시:

[스크립트 시작]
character-1: "다녀왔어요."
character-2: "어서 와, 점순아."
나레이션 - narration: 점순은 신발을 벗고 조심스레 방으로 들어왔다.
character-1: "오늘은 기분이 좀 이상해요."
[스크립트 끝]

[등장인물 업데이트 시작]
{
  "점순 - character-1": "처음에는 '여성'으로 등장했으나, 이름이 확인됨",
  "엄마 - character-2": "점순의 어머니로 추정됨",
  "아버지 - character-3": "무뚝뚝하지만 자상한 성격"
}
[등장인물 업데이트 끝]

🛑 위 목록에는 **이전에 등장한 모든 인물**을 반드시 포함하세요. 이름이 변경되었거나 새로 등장했다면 설명도 업데이트하세요.

---

📌 이전 등장인물 목록 (JSON 형식):
%s

---

📘 이번 파트 텍스트:
%s
""".formatted(jsonCharacters, chunkText);

        } catch (Exception e) {
            throw new RuntimeException("프롬프트 생성 실패", e);
        }
    }

}
