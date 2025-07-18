package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.infra.audio.VoiceConcatenator;
import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.inspection.exception.PendingBookException;
import com.speaktext.backend.book.script.application.implement.CharacterSearcher;
import com.speaktext.backend.book.script.application.implement.ScriptSearcher;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.script.domain.repository.ScriptRepository;
import com.speaktext.backend.book.script.exception.ScriptException;
import com.speaktext.backend.book.script.exception.ScriptExceptionType;
import com.speaktext.backend.book.script.exception.ScriptFragmentException;
import com.speaktext.backend.book.voice.application.dto.MergeRequestedResponse;
import com.speaktext.backend.book.voice.application.dto.VoiceStatusResponse;
import com.speaktext.backend.book.voice.application.dto.VoiceLengthInfoResponse;
import com.speaktext.backend.book.voice.application.dto.VoicePathResponse;
import com.speaktext.backend.book.voice.application.factory.CumulativeVoiceDurationFactory;
import com.speaktext.backend.book.voice.domain.CumulativeVoiceDuration;
import com.speaktext.backend.book.voice.domain.repository.VoiceStorage;
import com.speaktext.backend.book.voice.exception.VoiceException;
import com.speaktext.backend.book.voice.exception.VoiceExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static com.speaktext.backend.book.inspection.exception.PendingBookExceptionType.PENDING_BOOK_NOT_FOUND;
import static com.speaktext.backend.book.script.domain.Script.VoiceStatus.MERGE_REQUESTED;
import static com.speaktext.backend.book.script.domain.Script.VoiceStatus.NOT_GENERATED;
import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;
import static com.speaktext.backend.book.script.exception.ScriptExceptionType.VOICE_STATUS_NOT_MERGE_REQUESTED;
import static com.speaktext.backend.book.script.exception.ScriptFragmentExceptionType.SCRIPT_FRAGMENT_NOT_FOUND;
import static com.speaktext.backend.book.voice.exception.VoiceExceptionType.NO_VOICE;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoiceService {

    private final ScriptSearcher scriptSearcher;
    private final CharacterSearcher characterSearcher;
    private final VoiceDispatcher voiceDispatcher;
    private final ScriptFragmentRepository scriptFragmentRepository;
    private final VoiceConcatenator voiceConcatenator;
    private final VoiceStorage voiceStorage;
    private final ScriptRepository scriptRepository;
    private final CumulativeVoiceDurationFactory cumulativeVoiceDurationFactory;
    private final PendingBookRepository pendingBookRepository;

    public void generateVoice(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        if (script.getVoiceStatus() != Script.VoiceStatus.NOT_GENERATED) {
            throw new ScriptException(ScriptExceptionType.ALREADY_VOICE_GENERATED);
        }

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

    @Async
    @Transactional
    public void mergeVoice(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));
        validateVoiceStatus(script);
        List<ScriptFragment> scriptFragments = scriptFragmentRepository.findByIdentificationNumberOrderByIndex(identificationNumber);
        validateScriptFragment(scriptFragments);
        List<File> voiceFiles = scriptFragments.stream()
                .map(ScriptFragment::getVoicePath)
                .map(voiceStorage::getVoiceFileWithFilePath)
                .toList();

        Path outputPath = voiceConcatenator.concatenate(voiceFiles, identificationNumber);
        log.info("Merged voice file created at: {}", outputPath);
        CumulativeVoiceDuration cumulativeVoiceDuration = cumulativeVoiceDurationFactory.fromFragments(scriptFragments);
        String voiceLengthInfo = cumulativeVoiceDuration.getJson();
        log.info("Cumulative voice duration: {}", voiceLengthInfo);
        script.markVoiceStatusAsMergedVoiceGenerated();
        script.updateMergedVoicePathAndVoiceLengthInfo(outputPath.toString(), voiceLengthInfo);
        scriptRepository.save(script);
    }

    private void validateVoiceStatus(Script script) {
        if (script.getVoiceStatus() != MERGE_REQUESTED) {
            throw new ScriptException(VOICE_STATUS_NOT_MERGE_REQUESTED);
        }
    }

    private void validateScriptFragment(List<ScriptFragment> scriptFragments) {
        if(scriptFragments.isEmpty()) {
            throw new ScriptFragmentException(SCRIPT_FRAGMENT_NOT_FOUND);
        }
    }

    public VoicePathResponse downloadVoice(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        return new VoicePathResponse(script.getMergedVoicePath());
    }

    public VoiceLengthInfoResponse getVoiceLengthInfo(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        if (script.getVoiceLengthInfo() == null) {
            throw new VoiceException(NO_VOICE);
        }

        return new VoiceLengthInfoResponse(script.getVoiceLengthInfo());
    }

    @Transactional
    public VoiceStatusResponse getVoiceStatus(String identificationNumber) {
        if(isPendingBookDone(identificationNumber)) return new VoiceStatusResponse("DONE");

        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        proceedWithVoiceGeneration(script);

        return new VoiceStatusResponse(script.getVoiceStatus().toString());
    }

    private boolean isPendingBookDone(String identificationNumber) {
        PendingBook pendingBook = pendingBookRepository.findByIdentificationNumber(identificationNumber);
        if (pendingBook == null) {
            throw new PendingBookException(PENDING_BOOK_NOT_FOUND);
        }
        return pendingBook.isDone();
    }

    private void proceedWithVoiceGeneration(Script script) {
        if (script.getVoiceStatus() == NOT_GENERATED) {
            Optional<ScriptFragment> nullVoicePathFragment =
                    scriptSearcher.findScriptFragmentsByIdentificationNumber(script.getIdentificationNumber())
                    .stream()
                    .filter(scriptFragment -> scriptFragment.getVoicePath() == null)
                    .findFirst();

            if (nullVoicePathFragment.isEmpty()) {
                script.markVoiceStatusAsFragmentsGenerated();
                scriptRepository.save(script);
            }
        }
    }

    @Transactional
    public void requestMergeVoiceGeneration(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        script.markVoiceStatusAsMergeRequested();
        scriptRepository.save(script);
    }

    public List<MergeRequestedResponse> getMergeRequested() {
        List<Script> mergeRequested = scriptSearcher.findMergeRequested();
        return mergeRequested.stream()
                .map(script -> new MergeRequestedResponse(script.getIdentificationNumber(), script.getTitle()))
                .toList();
    }

    public Path getMergedVoice(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        if (script.getMergedVoicePath() == null) {
            throw new VoiceException(VoiceExceptionType.MERGED_VOICE_NOT_FOUND);
        }
        return Path.of(script.getMergedVoicePath());
    }
}
