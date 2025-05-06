package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBookChunk;
import com.speaktext.backend.book.domain.PendingBookChunks;
import com.speaktext.backend.book.domain.repository.PendingBookChunkRepository;
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
