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

    private static final Pattern SCRIPT_PATTERN = Pattern.compile("\\[스크립트 시작\\](.*?)\\[스크립트 끝\\]", Pattern.DOTALL);
    private static final Pattern CHARACTER_PATTERN = Pattern.compile("\\[등장인물 업데이트 시작\\](.*?)\\[등장인물 업데이트 끝\\]", Pattern.DOTALL);

    public ScriptGenerationResult parse(String content) {
        String scriptText = extractSection(content, SCRIPT_PATTERN);
        String characterJson = extractSection(content, CHARACTER_PATTERN);

        List<ScriptFragment> fragments = parseFragments(scriptText);
        Map<String, CharacterInfoDto> updatedCharacters = parseCharacterMap(characterJson);

        return new ScriptGenerationResult(fragments, updatedCharacters);
    }

    private String extractSection(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

    private List<ScriptFragment> parseFragments(String scriptText) {
        List<ScriptFragment> fragments = new ArrayList<>();

        Arrays.stream(scriptText.split("\n"))
                .map(String::trim)
                .filter(line -> line.contains(":"))
                .forEach(line -> {
                    int sepIndex = line.indexOf(":");
                    String speaker = line.substring(0, sepIndex).trim();
                    String utterance = line.substring(sepIndex + 1).trim().replaceAll("^\"|\"$", "");

                    fragments.add(ScriptFragment.builder()
                            .speaker(speaker)
                            .utterance(utterance)
                            .build());
                });

        return fragments;
    }

    private Map<String, CharacterInfoDto> parseCharacterMap(String characterJson) {
        Map<String, CharacterInfoDto> characterMap = new LinkedHashMap<>();

        try {
            Map<String, String> rawMap = objectMapper.readValue(characterJson, new com.fasterxml.jackson.core.type.TypeReference<>() {});
            for (Map.Entry<String, String> entry : rawMap.entrySet()) {
                String[] parts = entry.getKey().split(" - ");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String characterId = parts[1].trim();
                    characterMap.put(characterId, new CharacterInfoDto(name, entry.getValue(), characterId));
                }
            }
        } catch (Exception e) {
            log.warn("⚠️ 등장인물 JSON 파싱 실패", e);
        }

        return characterMap;
    }
}