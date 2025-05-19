package com.speaktext.backend.book.voice.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.infra.audio.VoiceConcatenator;
import com.speaktext.backend.book.script.application.implement.CharacterSearcher;
import com.speaktext.backend.book.script.application.implement.ScriptSearcher;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.script.domain.repository.ScriptRepository;
import com.speaktext.backend.book.script.exception.ScriptException;
import com.speaktext.backend.book.script.exception.ScriptFragmentException;
import com.speaktext.backend.book.voice.domain.CumulativeVoiceDuration;
import com.speaktext.backend.book.voice.domain.repository.VoiceStorage;
import com.speaktext.backend.book.voice.exception.VoiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;
import static com.speaktext.backend.book.script.exception.ScriptFragmentExceptionType.SCRIPT_FRAGMENT_NOT_FOUND;
import static com.speaktext.backend.book.voice.exception.VoiceExceptionType.NO_VOICE;

@Service
@RequiredArgsConstructor
public class VoiceService {

    private final ScriptSearcher scriptSearcher;
    private final CharacterSearcher characterSearcher;
    private final VoiceDispatcher voiceDispatcher;
    private final ObjectMapper objectMapper;
    private final ScriptFragmentRepository scriptFragmentRepository;
    private final VoiceConcatenator voiceConcatenator;
    private final VoiceStorage voiceStorage;
    private final ScriptRepository scriptRepository;

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
            if (!character.hasVoiceOrNotAppeared()) {
                throw new VoiceException(NO_VOICE);
            }
        });
    }

    public void mergeVoice(String identificationNumber) {
        List<ScriptFragment> scriptFragments = scriptFragmentRepository.findByIdentificationNumberOrderByIndex(identificationNumber);
        validateScriptFragment(scriptFragments);
        List<File> voiceFiles = scriptFragments.stream()
                .map(ScriptFragment::getVoicePath)
                .map(voiceStorage::getVoiceFileWithFilePath)
                .toList();

        Path outputPath = voiceConcatenator.concatenate(voiceFiles, identificationNumber);
        String voiceLengthInfo = CumulativeVoiceDuration.fromFragments(scriptFragments, objectMapper).getJson();
        scriptRepository.saveMergedVoicePathAndVoiceLengthInfo(identificationNumber, outputPath.toString(), voiceLengthInfo);
    }

    private void validateScriptFragment(List<ScriptFragment> scriptFragments) {
        if(scriptFragments.isEmpty()) {
            throw new ScriptFragmentException(SCRIPT_FRAGMENT_NOT_FOUND);
        }
    }
}
