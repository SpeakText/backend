package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBookChunks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScriptProcessingPipeline {

    private final ScriptPartitioner scriptPartitioner;
    private final ChunkDispatcher chunkDispatcher;
    private final ScriptBuilder scriptBuilder;

    public void prepareScriptGeneration(Long pendingBookId) {
        PendingBookChunks chunks = scriptPartitioner.split(pendingBookId);
        scriptBuilder.build(chunks, pendingBookId);
        chunkDispatcher.dispatch(chunks);
    }

}
