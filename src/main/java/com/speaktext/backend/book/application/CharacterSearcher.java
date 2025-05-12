package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.CharacterDto;
import com.speaktext.backend.book.domain.ScriptCharacter;
import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CharacterSearcher {

    private final CharacterRepository characterRepository;

    public List<CharacterDto> findCharactersByScript(Script script) {
        List<ScriptCharacter> scriptCharacters = characterRepository.findByScript(script);
        return scriptCharacters.stream().
                map(CharacterDto::fromDomain)
                .toList();
    }

}
