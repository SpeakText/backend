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

    public void generateScript(Long pendingBookId) {
        scriptInvoker.announce(pendingBookId);
    }

    public void splitPendingBookChunk(Long pendingBookId) {
        PendingBookChunks chunks = scriptPartitioner.split(pendingBookId);
        chunkDispatcher.dispatch(chunks);
    }

}
