package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.PendingBookChunk;
import com.speaktext.backend.book.domain.repository.PendingBookChunkRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PendingBookChunkRepositoryImpl implements PendingBookChunkRepository {

    private final PendingBookChunkJpaRepository pendingBookChunkJpaRepository;

    public PendingBookChunkRepositoryImpl(PendingBookChunkJpaRepository pendingBookChunkJpaRepository) {
        this.pendingBookChunkJpaRepository = pendingBookChunkJpaRepository;
    }

    @Override
    public void save(PendingBookChunk pendingBookChunk) {
        pendingBookChunkJpaRepository.save(pendingBookChunk);
    }

    @Override
    public void saveAll(List<PendingBookChunk> pendingBookChunks) {
        pendingBookChunkJpaRepository.saveAll(pendingBookChunks);
    }

}
