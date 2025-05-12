package com.speaktext.backend.book.script.application;

import com.speaktext.backend.book.script.application.implement.CharacterManager;
import com.speaktext.backend.book.script.application.dto.CharacterUpdatedResponse;
import com.speaktext.backend.book.script.application.dto.CharactersUpdateCommand;
import com.speaktext.backend.book.script.domain.ScriptCharacter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterManager characterManager;

    public CharacterUpdatedResponse update(CharactersUpdateCommand command) {
        List<ScriptCharacter> scriptCharacters = characterManager.updateCharacters(command);
        return CharacterUpdatedResponse.of(scriptCharacters);
    }

}
