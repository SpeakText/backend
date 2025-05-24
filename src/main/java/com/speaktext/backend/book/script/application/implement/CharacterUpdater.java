package com.speaktext.backend.book.script.application.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.script.application.dto.CharacterDto;
import com.speaktext.backend.book.script.domain.CharacterVoiceType;
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

        // 기존 캐릭터 map으로 구성 (key: characterKey)
        Map<String, CharacterDto> characterMap = new LinkedHashMap<>();
        existingCharacters.forEach(dto -> characterMap.put(dto.characterKey(), dto));

        // 새로 파싱된 캐릭터 추가 (없으면 넣고, 있으면 무시/유지)
        newCharacters.forEach(dto -> characterMap.putIfAbsent(dto.characterKey(), dto));

        // 등장여부 업데이트 (발화문 기준으로만 true)
        // 이전 true 유지
        return characterMap.values().stream()
                .map(dto -> new CharacterDto(
                        dto.name(),
                        dto.description(),
                        dto.characterKey(),
                        appearedCharacterKeys.contains(dto.characterKey()) || dto.appearedInScript(),
                        CharacterVoiceType.NO_VOICE
                ))
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
                    characterList.add(new CharacterDto(name, entry.getValue(), characterId, false, CharacterVoiceType.NO_VOICE));
                }
            }
        } catch (Exception e) {
            log.warn("등장인물 JSON 파싱 실패", e);
        }

        return characterList;
    }

}
