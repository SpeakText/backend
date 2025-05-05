package com.speaktext.backend.book.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.application.dto.CharacterInfoDto;
import com.speaktext.backend.book.application.dto.ScriptGenerationResult;
import com.speaktext.backend.book.domain.ScriptFragment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScriptParser {

    private final ObjectMapper objectMapper;

    public ScriptGenerationResult parse(String content) {
        Pattern scriptPattern = Pattern.compile("\\[스크립트 시작\\](.*?)\\[스크립트 끝\\]", Pattern.DOTALL);
        Pattern characterPattern = Pattern.compile("\\[등장인물 업데이트 시작\\](.*?)\\[등장인물 업데이트 끝\\]", Pattern.DOTALL);

        Matcher scriptMatcher = scriptPattern.matcher(content);
        Matcher characterMatcher = characterPattern.matcher(content);

        String scriptText = scriptMatcher.find() ? scriptMatcher.group(1).trim() : "";
        String characterJson = characterMatcher.find() ? characterMatcher.group(1).trim() : "{}";

        List<ScriptFragment> fragments = new ArrayList<>();
        int order = 0;
        for (String line : scriptText.split("\n")) {
            line = line.trim();
            if (line.isEmpty()) continue;

            int sepIndex = line.indexOf(":");
            if (sepIndex < 0) continue;

            String speaker = line.substring(0, sepIndex).trim(); // ex: character-1
            String utterance = line.substring(sepIndex + 1).trim().replaceAll("^\"|\"$", "");

            fragments.add(ScriptFragment.builder()
                    .orderIndex(order++)
                    .speaker(speaker)
                    .utterance(utterance)
                    .build());
        }

        Map<String, CharacterInfoDto> updatedCharacters = new LinkedHashMap<>();
        try {
            Map<String, String> raw = objectMapper.readValue(characterJson, new com.fasterxml.jackson.core.type.TypeReference<>() {});
            for (Map.Entry<String, String> entry : raw.entrySet()) {
                String key = entry.getKey();
                String[] parts = key.split(" - ");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String characterId = parts[1].trim();
                    String description = entry.getValue();
                    updatedCharacters.put(characterId, new CharacterInfoDto(name, description, characterId));
                }
            }
        } catch (Exception e) {
            log.warn("⚠️ 등장인물 JSON 파싱 실패", e);
        }

        return new ScriptGenerationResult(fragments, updatedCharacters);
    }

}