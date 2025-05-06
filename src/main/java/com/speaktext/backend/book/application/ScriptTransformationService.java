package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBookChunks;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScriptTransformationService {

    private final ScriptInvoker scriptInvoker;
    private final ScriptPartitioner scriptPartitioner;
    private final ChunkDispatcher chunkDispatcher;
    private final ScriptBuilder scriptBuilder;

    public void announceScriptGeneration(Long pendingBookId) {
        scriptInvoker.announce(pendingBookId);
    }

    public void prepareScriptGeneration(Long pendingBookId) {
        PendingBookChunks chunks = scriptPartitioner.split(pendingBookId);
        scriptBuilder.build(chunks, pendingBookId);
        chunkDispatcher.dispatch(chunks);
    }

}
