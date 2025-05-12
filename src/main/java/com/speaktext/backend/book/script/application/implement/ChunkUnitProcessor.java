package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.domain.event.ChunkProcessingEvent;
import com.speaktext.backend.book.script.application.publisher.ChunkMessagePublisher;
import com.speaktext.backend.book.script.domain.PendingBookChunk;
import com.speaktext.backend.book.script.domain.repository.PendingBookChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ChunkUnitProcessor {

    private final PendingBookChunkRepository repository;
    private final ChunkMessagePublisher publisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void process(PendingBookChunk chunk) {
        try {
            publisher.publish(new ChunkProcessingEvent(chunk.getPendingBookChunkId()));
            chunk.markAsSent();
        } catch (Exception e) {
            chunk.markAsFailed();
            System.err.println("Dispatch error: " + e.getMessage());
        }
        repository.save(chunk);
    }

}
