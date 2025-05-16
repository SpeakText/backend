package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.application.dto.CharacterDto;
import com.speaktext.backend.book.script.application.dto.ScriptGenerationResult;
import com.speaktext.backend.book.script.domain.PendingBookChunk;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.repository.PendingBookChunkRepository;
import com.speaktext.backend.book.script.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.script.domain.repository.ScriptRepository;
import com.speaktext.backend.book.script.exception.ScriptException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScriptGenerator {

    private final ScriptSearcher scriptSearcher;
    private final PendingBookChunkRepository pendingBookChunkRepository;
    private final ScriptRepository scriptRepository;
    private final ScriptInterpreter scriptInterpreter;
    private final ScriptFragmentRepository scriptFragmentRepository;
    private final CharacterManager characterManager;
    private final CharacterSearcher characterSearcher;

    public void generate(Long pendingBookChunkId) {
        PendingBookChunk chunk = pendingBookChunkRepository.findById(pendingBookChunkId);
        String identificationNumber = chunk.getIdentificationNumber();
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        List<CharacterDto> mainCharacters = characterSearcher.findCharactersDtoByScript(script);
        ScriptGenerationResult generated = scriptInterpreter.generate(chunk.getChunk(), mainCharacters);
        updateScriptStatus(script, generated);
    }

    private void updateScriptStatus(Script script, ScriptGenerationResult generated) {
        long startIndex = scriptSearcher.findLastScriptFragment(script.getIdentificationNumber())
                .map(ScriptFragment::getIndex)
                .orElse(0L);

        AtomicLong orderCounter = new AtomicLong(startIndex);
        characterManager.inferCharacters(script, generated.updatedCharacters());
        script.increaseProgress();

        generated.fragments().forEach(fragment -> {
            fragment.confirmIdentificationNumber(script.getIdentificationNumber());
            fragment.setIndex(orderCounter.incrementAndGet());
        });

        scriptRepository.save(script);
        scriptFragmentRepository.saveAll(generated.fragments());
    }

}
