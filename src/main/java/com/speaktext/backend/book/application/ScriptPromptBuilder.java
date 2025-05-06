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

            return ScriptPromptTemplate.TEMPLATE.formatted(jsonCharacters, chunkText);
        } catch (Exception e) {
            throw new RuntimeException("프롬프트 생성 실패", e);
        }
    }

}