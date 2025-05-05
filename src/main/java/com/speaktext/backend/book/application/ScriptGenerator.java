package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBookChunk;
import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.ScriptFragment;
import com.speaktext.backend.book.domain.repository.PendingBookChunkRepository;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ScriptGenerator {

    private final ScriptFinder scriptFinder;
    private final PendingBookChunkRepository pendingBookChunkRepository;
    private final ScriptRepository scriptRepository;
    private final LLMGenerator llmGenerator;

    public void generate(Long pendingBookChunkId) {
        PendingBookChunk chunk = pendingBookChunkRepository.findById(pendingBookChunkId);
        String identificationNumber = chunk.getIdentificationNumber();
        Script script = scriptFinder.findByIdentificationNumber(identificationNumber)
                .orElseGet(() -> {
                    Script newScript = Script.createInitial(identificationNumber);
                    return scriptRepository.save(newScript);
                });
        Map<String, String> mainCharacters = script.getMainCharacters();
        llmGenerator.generate(chunk.getChunk(), mainCharacters);
    }

}
