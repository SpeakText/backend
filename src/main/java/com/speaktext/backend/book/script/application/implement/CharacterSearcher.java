package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.application.dto.CharacterDto;
import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterSearcher {

    private final CharacterRepository characterRepository;

    public List<CharacterDto> findCharactersDtoByScript(Script script) {
        List<ScriptCharacter> scriptCharacters = characterRepository.findByScript(script);
        return scriptCharacters.stream().
                map(CharacterDto::fromDomain)
                .toList();
    }

    public List<ScriptCharacter> findScriptCharactersByScript(Script script) {
        return characterRepository.findByScript(script);
    }

}
