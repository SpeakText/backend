package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBookChunk;
import com.speaktext.backend.book.domain.repository.PendingBookChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PendingBookChunkFinder {

    private final PendingBookChunkRepository pendingBookChunkRepository;

    public PendingBookChunk find(Long pendingBookChunkId) {
        return pendingBookChunkRepository.findById(pendingBookChunkId);
    }

}
