package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.CharacterInfoDto;
import com.speaktext.backend.book.application.dto.ScriptGenerationResult;
import com.speaktext.backend.book.domain.PendingBookChunk;
import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.repository.PendingBookChunkRepository;
import com.speaktext.backend.book.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import com.speaktext.backend.book.exception.BookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.speaktext.backend.book.exception.BookExceptionType.SCRIPT_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScriptGenerator {

    private final ScriptSearcher scriptSearcher;
    private final PendingBookChunkRepository pendingBookChunkRepository;
    private final ScriptRepository scriptRepository;
    private final LLMGenerator llmGenerator;
    private final ScriptFragmentRepository scriptFragmentRepository;

    public void generate(Long pendingBookChunkId) {
        PendingBookChunk chunk = pendingBookChunkRepository.findById(pendingBookChunkId);
        String identificationNumber = chunk.getIdentificationNumber();
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new BookException(SCRIPT_NOT_FOUND));
        Map<String, CharacterInfoDto> mainCharacters = script.getMainCharacters();
        ScriptGenerationResult generated = llmGenerator.generate(chunk.getChunk(), mainCharacters);
        updateScriptStatus(script, generated);
    }

    private void updateScriptStatus(Script script, ScriptGenerationResult generated) {
        script.updateCharacterInfo(generated.updatedCharacters());
        script.increaseProgress();
        generated.fragments().forEach(
                scriptFragment -> scriptFragment.confirmIdentificationNumber(script.getIdentificationNumber())
        );
        scriptRepository.save(script);
        scriptFragmentRepository.saveAll(generated.fragments());
    }

}
