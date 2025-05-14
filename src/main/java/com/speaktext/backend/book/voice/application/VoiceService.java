package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.application.implement.CharacterSearcher;
import com.speaktext.backend.book.script.application.implement.ScriptSearcher;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.exception.ScriptException;
import com.speaktext.backend.book.voice.exception.VoiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;
import static com.speaktext.backend.book.voice.exception.VoiceExceptionType.NO_VOICE;

@Service
@RequiredArgsConstructor
public class VoiceService {

    private final ScriptSearcher scriptSearcher;
    private final CharacterSearcher characterSearcher;
    private final VoiceDispatcher voiceDispatcher;

    public void generateVoice(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        List<ScriptCharacter> scriptCharacters = characterSearcher.findScriptCharactersByScript(script);
        validate(script, scriptCharacters);
        voiceDispatcher.dispatch(identificationNumber, script);
    }

    private void validate(Script script, List<ScriptCharacter> scriptCharacters) {
        if (!script.hasVoice()) {
            throw new VoiceException(NO_VOICE);
        }
        scriptCharacters.forEach(character -> {
            if (!character.hasVoice()) {
                throw new VoiceException(NO_VOICE);
            }
        });
    }

}
