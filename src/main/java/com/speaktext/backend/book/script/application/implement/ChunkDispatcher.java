package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.domain.PendingBookChunk;
import com.speaktext.backend.book.script.domain.PendingBookChunks;
import com.speaktext.backend.book.script.domain.repository.PendingBookChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ChunkDispatcher {

    private final PendingBookChunkRepository repository;
    private final ChunkUnitProcessor processor;

    public void dispatch(PendingBookChunks chunks) {
        List<PendingBookChunk> pendingBookChunkNotSent = chunks.getPendingBookChunkNotSent();
        repository.saveAll(pendingBookChunkNotSent);
        pendingBookChunkNotSent.forEach(processor::process);
    }

}
