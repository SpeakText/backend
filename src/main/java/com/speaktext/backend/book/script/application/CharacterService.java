package com.speaktext.backend.book.script.application;

import com.speaktext.backend.book.script.application.dto.CharacterDto;
import com.speaktext.backend.book.script.application.implement.CharacterManager;
import com.speaktext.backend.book.script.application.dto.CharacterUpdatedResponse;
import com.speaktext.backend.book.script.application.dto.CharactersUpdateCommand;
import com.speaktext.backend.book.script.application.implement.CharacterSearcher;
import com.speaktext.backend.book.script.application.implement.ScriptSearcher;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.exception.ScriptException;
import com.speaktext.backend.book.script.exception.ScriptExceptionType;
import com.speaktext.backend.book.script.presentation.dto.CharacterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CharacterService {

    private final CharacterManager characterManager;
    private final CharacterSearcher characterSearcher;
    private final ScriptSearcher scriptSearcher;

    public CharacterUpdatedResponse update(CharactersUpdateCommand command) {
        List<ScriptCharacter> scriptCharacters = characterManager.updateCharacters(command);
        return CharacterUpdatedResponse.of(scriptCharacters);
    }

    public List<CharacterResponse> getCharacters(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        List<CharacterDto> characters = characterSearcher.findCharactersByScript(script);
        return characters.stream()
                .filter(CharacterDto::appearedInScript)
                .map(characterDto ->
                        new CharacterResponse(
                                characterDto.characterKey(),
                                characterDto.name(),
                                characterDto.voiceType().toString())
                )
                .toList();
    }

}
