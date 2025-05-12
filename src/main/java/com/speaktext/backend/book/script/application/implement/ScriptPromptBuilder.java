package com.speaktext.backend.book.script.application.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.script.application.dto.CharacterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScriptPromptBuilder {

    private final ObjectMapper objectMapper;

    public String build(String chunkText, List<CharacterDto> characterDescriptions) {
        try {
            Map<String, String> promptCharacterMap = characterDescriptions.stream()
                    .collect(Collectors.toMap(
                            c -> c.name() + " - " + c.characterKey(),
                            CharacterDto::description
                    ));

            String jsonCharacters = objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(promptCharacterMap);

            return ScriptPromptTemplate.TEMPLATE.formatted(jsonCharacters, chunkText);
        } catch (Exception e) {
            throw new RuntimeException("프롬프트 생성 실패", e);
        }
    }

}