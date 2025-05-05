package com.speaktext.backend.book.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class LLMGenerator {

//    private final LanguageModelClient client;
    private final ObjectMapper objectMapper;

    public void generate(String chunkText, Map<String, String> mainCharacters) {
//        String systemPrompt = "당신은 소설을 스크립트 형태로 재구성하는 언어 분석가입니다.";
//        String userPrompt = buildPrompt(chunkText, mainCharacters);
//
//        ScriptGenerationRequest request = ScriptGenerationRequest.builder()
//                .model("gpt-4o-mini")
//                .temperature(0.7)
//                .messages(List.of(
//                        ScriptGenerationRequest.Message.builder().role("system").content(systemPrompt).build(),
//                        ScriptGenerationRequest.Message.builder().role("user").content(userPrompt).build()
//                ))
//                .build();
//
//        ScriptGenerationResponse response = client.generate(request);
//        String content = response.getChoices()[0].getMessage().getContent();
        log.info(chunkText);
//        return parseScriptFragments(content);
    }

//    private String buildPrompt(String chunkText, Map<String, String> characterDescriptions) {
//        try {
//            String jsonCharacters = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(characterDescriptions);
//            return """
//                    다음은 소설의 일부입니다. 이 텍스트를 발화자-발화문, 나레이션으로 구성된 스크립트 형식으로 변환해주세요.
//
//                    요구 형식:
//                    - "나레이션": "문장"
//                    - "등장인물 이름 또는 유추된 호칭": "발화문"
//
//                    규칙:
//                    - 나레이션이면 하나의 문장, 발화문이면 따움표 안의 내용이 들어가야 합니다.
//                    - 말하는 사람이 확실하지 않으면, 등장인물 설명을 참고해서 가장 알맞은 이름이나 호칭("엄마", "지수", "선배", "철수" 등)을 직접 사용해주세요.
//                    - '나'라는 표현이 등장하면, 그 인물은 항상 주인공으로 간주해주세요. 이름이 이미 등장했다면 그 이름을 사용하고, 그렇지 않으면 '1인칭 화자' 라는 식으로 표현해주세요.
//                    - 가능한 경우 '인물1' 같은 임시 이름은 사용하지 마세요.
//                    - 새로운 인물이 등장하면 이름이나 호칭과 함께 설명을 추가해주세요. (예: "지수": "결단력 있는 남성, 주인공으로 추정")
//                    - 등장인물 정보가 변경되지 않았다면 그대로 두세요.
//
//                    응답 형식:
//                    [스크립트 시작]
//                    (스크립트 형식으로 변환된 내용)
//                    [스크립트 끝]
//
//                    [등장인물 업데이트 시작]
//                    (변경되었거나 유지된 등장인물 JSON. 이름/호칭을 기준으로 작성)
//                    [등장인물 업데이트 끝]
//
//                    이전 등장인물 목록 (JSON 형식):
//                    %s
//
//                    이번 파트:
//                    %s
//                    """.formatted(jsonCharacters, chunkText);
//        } catch (Exception e) {
//            throw new RuntimeException("프롬프트 생성 실패", e);
//        }
//    }
//
//    private List<ScriptFragment> parseScriptFragments(String content) {
//        Pattern scriptPattern = Pattern.compile("\\[스크립트 시작\\](.*?)\\[스크립트 끝\\]", Pattern.DOTALL);
//        Pattern characterPattern = Pattern.compile("\\[등장인물 업데이트 시작\\](.*?)\\[등장인물 업데이트 끝\\]", Pattern.DOTALL);
//
//        Matcher scriptMatcher = scriptPattern.matcher(content);
//        Matcher characterMatcher = characterPattern.matcher(content);
//
//        String scriptText = "";
//        String characterJson = "{}";
//
//        if (scriptMatcher.find()) {
//            scriptText = scriptMatcher.group(1).trim();
//        }
//
//        if (characterMatcher.find()) {
//            characterJson = characterMatcher.group(1).trim();
//        }
//
//        List<ScriptFragment> fragments = new ArrayList<>();
//        int order = 0;
//
//        for (String line : scriptText.split("\n")) {
//            line = line.trim();
//            if (line.isEmpty()) continue;
//
//            int sepIndex = line.indexOf(":");
//            if (sepIndex < 0) continue;
//
//            String speaker = line.substring(0, sepIndex).replaceAll("\"", "").trim();
//            String utterance = line.substring(sepIndex + 1).trim().replaceAll("^\"|\"$", "");
//
//            fragments.add(ScriptFragment.builder()
//                    .orderIndex(order++)
//                    .speaker(speaker)
//                    .utterance(utterance)
//                    .build());
//        }
//
//        try {
//            Map<String, String> updatedCharacters = objectMapper.readValue(characterJson, new TypeReference<>() {});
//            // 이 updatedCharacters 를 Script.mainCharacters 에 병합할 수 있음
//        } catch (Exception e) {
//            log.warn("⚠️ 등장인물 JSON 파싱 실패", e);
//        }
//
//        return fragments;
//    }

}
