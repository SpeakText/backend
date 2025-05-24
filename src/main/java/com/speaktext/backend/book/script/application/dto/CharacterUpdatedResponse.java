package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.ScriptCharacter;

import java.util.List;

public record CharacterUpdatedResponse(
        List<UpdatedCharacter> updatedCharacters
) {

    public static CharacterUpdatedResponse of(List<ScriptCharacter> scriptCharacters) {
        List<UpdatedCharacter> updatedList = scriptCharacters.stream()
                .map(c -> new UpdatedCharacter(c.getCharacterKey(), c.getName(), c.getCharacterVoiceType().name()))
                .toList();

        return new CharacterUpdatedResponse(updatedList);
    }

    public record UpdatedCharacter(
            String characterKey,
            String name,
            String voiceType
    ) {}

}