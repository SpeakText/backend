package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.repository.CharacterRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CharacterRepositoryImpl implements CharacterRepository {

    private final CharacterJpaRepository characterJpaRepository;

    public CharacterRepositoryImpl(CharacterJpaRepository characterJpaRepository) {
        this.characterJpaRepository = characterJpaRepository;
    }

    @Override
    public void saveAll(List<ScriptCharacter> scriptCharacters) {
        characterJpaRepository.saveAll(scriptCharacters);
    }

    @Override
    public void deleteByScript(Script script) {
        characterJpaRepository.deleteByScript(script);
    }

    @Override
    public List<ScriptCharacter> findByScript(Script script) {
        return characterJpaRepository.findByScript(script);
    }

    @Override
    public Optional<ScriptCharacter> findByScriptAndCharacterKey(Script script, String characterKey) {
        return characterJpaRepository.findByScriptAndCharacterKey(script, characterKey);
    }

}
