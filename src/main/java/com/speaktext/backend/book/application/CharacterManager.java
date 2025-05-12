package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.CharacterDto;
import com.speaktext.backend.book.application.dto.CharactersUpdateCommand;
import com.speaktext.backend.book.domain.ScriptCharacter;
import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.VoiceType;
import com.speaktext.backend.book.domain.repository.CharacterRepository;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import com.speaktext.backend.book.exception.ScriptException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.speaktext.backend.book.exception.ScriptExceptionType.CHARACTER_NOT_FOUND;
import static com.speaktext.backend.book.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class CharacterManager {

    private final CharacterRepository characterRepository;
    private final ScriptRepository scriptRepository;

    @Transactional
    public void inferCharacters(Script script, List<CharacterDto> updatedCharacters) {
        characterRepository.deleteByScript(script);

        List<ScriptCharacter> scriptCharacters = updatedCharacters.stream()
                .map(characterDto -> characterDto.toDomain(script))
                .toList();

        characterRepository.saveAll(scriptCharacters);
    }

    @Transactional
    public List<ScriptCharacter> updateCharacters(CharactersUpdateCommand command) {
        Script script = scriptRepository.findById(command.scriptId())
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        return command.updatedContent().stream().
                map(each ->
                        updateNameAndVoice(
                                script,
                                each.characterKey(),
                                each.name(),
                                each.voiceType()
                        )
                ).toList();
    }

    private ScriptCharacter updateNameAndVoice(Script script, String characterKey, String newName, VoiceType newVoice) {
        ScriptCharacter scriptCharacter = characterRepository.findByScriptAndCharacterKey(script, characterKey)
                .orElseThrow(() -> new ScriptException(CHARACTER_NOT_FOUND));
        scriptCharacter.updateName(newName);
        scriptCharacter.updateVoice(newVoice);
        return scriptCharacter;
    }

}
