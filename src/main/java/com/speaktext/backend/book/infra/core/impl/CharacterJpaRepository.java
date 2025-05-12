package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.ScriptCharacter;
import com.speaktext.backend.book.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterJpaRepository extends JpaRepository<ScriptCharacter, Long> {

    void deleteByScript(Script script);
    List<ScriptCharacter> findByScript(Script script);
    Optional<ScriptCharacter> findByScriptAndCharacterKey(Script script, String characterKey);

}
