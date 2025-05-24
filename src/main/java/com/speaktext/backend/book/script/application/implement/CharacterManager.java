package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.application.dto.CharacterDto;
import com.speaktext.backend.book.script.application.dto.CharactersUpdateCommand;
import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.script.domain.repository.CharacterRepository;
import com.speaktext.backend.book.script.domain.repository.ScriptRepository;
import com.speaktext.backend.book.script.exception.ScriptException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.speaktext.backend.book.script.exception.ScriptExceptionType.CHARACTER_NOT_FOUND;
import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CharacterManager {

    private final CharacterRepository characterRepository;
    private final ScriptRepository scriptRepository;

    @Transactional
    public void inferCharacters(Script script, List<CharacterDto> updatedCharacters) {
        characterRepository.deleteByScript(script);

        List<ScriptCharacter> scriptCharacters = updatedCharacters.stream()
                .map(characterDto -> characterDto.toDomain(script, characterDto.appearedInScript()))
                .toList();

        characterRepository.saveAll(scriptCharacters);
    }

    @Transactional
    public List<ScriptCharacter> updateCharacters(CharactersUpdateCommand command) {
        Script script = scriptRepository.findByIdentificationNumber(command.identificationNumber())
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        return command.updatedContent().stream().
                map(each ->
                        updateNameAndVoice(
                                script,
                                each.characterKey(),
                                each.name(),
                                each.characterVoiceType()
                        )
                ).toList();
    }

    private ScriptCharacter updateNameAndVoice(Script script, String characterKey, String newName, CharacterVoiceType newVoice) {
        ScriptCharacter scriptCharacter = characterRepository.findByScriptAndCharacterKey(script, characterKey)
                .orElseThrow(() -> new ScriptException(CHARACTER_NOT_FOUND));
        scriptCharacter.updateName(newName);
        scriptCharacter.updateVoice(newVoice);
        return scriptCharacter;
    }

}
