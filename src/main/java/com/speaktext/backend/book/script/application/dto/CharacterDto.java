package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.CharacterVoiceType;

public record CharacterDto(
        String name, String description, String characterKey, boolean appearedInScript, CharacterVoiceType characterVoiceType
) {

    public ScriptCharacter toDomain(Script script, boolean appearedInScript) {
        return ScriptCharacter.init(name, description, characterKey, script, appearedInScript);
    }

    public static CharacterDto fromDomain(ScriptCharacter scriptCharacter) {
        return new CharacterDto(scriptCharacter.getName(), scriptCharacter.getDescription(), scriptCharacter.getCharacterKey(), scriptCharacter.isAppearedInScript(), scriptCharacter.getCharacterVoiceType());
    }

}
