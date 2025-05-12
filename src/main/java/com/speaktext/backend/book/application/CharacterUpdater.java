package com.speaktext.backend.book.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.application.dto.CharacterDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class CharacterUpdater {

    private final ObjectMapper objectMapper;

    /**
     * 스크립트 등장 여부 업데이트 함수
     */
    public List<CharacterDto> updateCharacterStates(
            String characterJson,
            Set<String> appearedCharacterKeys,
            List<CharacterDto> existingCharacters
    ) {
        List<CharacterDto> newCharacters = parseCharacterList(characterJson);

        // 기존 캐릭터: appeared 여부 업데이트
        List<CharacterDto> updatedExisting = existingCharacters.stream()
                .map(dto -> new CharacterDto(
                        dto.name(),
                        dto.description(),
                        dto.characterKey(),
                        appearedCharacterKeys.contains(dto.characterKey())
                ))
                .toList();

        // 새 캐릭터: appeared 여부 적용
        List<CharacterDto> updatedNew = newCharacters.stream()
                .map(dto -> new CharacterDto(
                        dto.name(),
                        dto.description(),
                        dto.characterKey(),
                        appearedCharacterKeys.contains(dto.characterKey())
                ))
                .toList();

        // 합치기
        return new LinkedHashMap<String, CharacterDto>() {{
            updatedExisting.forEach(dto -> put(dto.characterKey(), dto));
            updatedNew.forEach(dto -> put(dto.characterKey(), dto));
        }}.values().stream()
        .toList();
    }

    private List<CharacterDto> parseCharacterList(String characterJson) {
        List<CharacterDto> characterList = new java.util.ArrayList<>();

        try {
            Map<String, String> rawMap = objectMapper.readValue(characterJson, new com.fasterxml.jackson.core.type.TypeReference<>() {});
            for (Map.Entry<String, String> entry : rawMap.entrySet()) {
                String[] parts = entry.getKey().split(" - ");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String characterId = parts[1].trim();
                    characterList.add(new CharacterDto(name, entry.getValue(), characterId, false));
                }
            }
        } catch (Exception e) {
            log.warn("등장인물 JSON 파싱 실패", e);
        }

        return characterList;
    }

}
