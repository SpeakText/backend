package com.speaktext.backend.book.application.dto;

import com.speaktext.backend.book.domain.ScriptCharacter;
import com.speaktext.backend.book.domain.Script;

public record CharacterDto(
        String name, String description, String characterKey, boolean appearedInScript
) {

    public ScriptCharacter toDomain(Script script) {
        return ScriptCharacter.init(name, description, characterKey, script);
    }

    public static CharacterDto fromDomain(ScriptCharacter scriptCharacter) {
        return new CharacterDto(scriptCharacter.getName(), scriptCharacter.getDescription(), scriptCharacter.getCharacterKey(), scriptCharacter.isAppearedInScript());
    }

}
