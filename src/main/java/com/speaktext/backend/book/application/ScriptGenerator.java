package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.ScriptGenerationResult;
import com.speaktext.backend.book.domain.PendingBookChunk;
import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.repository.PendingBookChunkRepository;
import com.speaktext.backend.book.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScriptGenerator {

    private final ScriptFinder scriptFinder;
    private final PendingBookChunkRepository pendingBookChunkRepository;
    private final ScriptRepository scriptRepository;
    private final LLMGenerator llmGenerator;
    private final ScriptFragmentRepository scriptFragmentRepository;

    public void generate(Long pendingBookChunkId) {
        PendingBookChunk chunk = pendingBookChunkRepository.findById(pendingBookChunkId);
        String identificationNumber = chunk.getIdentificationNumber();
        Script script = scriptFinder.findByIdentificationNumber(identificationNumber)
                .orElseGet(() -> {
                    Script newScript = Script.createInitial(identificationNumber);
                    return scriptRepository.save(newScript);
                });
        Map<String, String> mainCharacters = script.getMainCharacters();
        ScriptGenerationResult generated = llmGenerator.generate(chunk.getChunk(), mainCharacters);
        script.updateCharacterInfo(generated.updatedCharacters());
        scriptRepository.save(script);
        log.info(generated.updatedCharacters().toString());
        for (var fragment : generated.fragments()) {
            log.info(fragment.getSpeaker());
            log.info(fragment.getUtterance());
        }
        scriptFragmentRepository.saveAll(generated.fragments());
    }

}
