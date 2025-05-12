package com.speaktext.backend.book.script.domain.repository;

import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.Script;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository {

    void saveAll(List<ScriptCharacter> scriptCharacters);
    void deleteByScript(Script script);
    List<ScriptCharacter> findByScript(Script script);
    Optional<ScriptCharacter> findByScriptAndCharacterKey(Script script, String characterKey);

}
