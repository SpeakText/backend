package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.script.domain.PendingBookChunk;
import com.speaktext.backend.book.script.domain.repository.PendingBookChunkRepository;
import com.speaktext.backend.book.script.exception.BookException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.speaktext.backend.book.script.exception.BookExceptionType.NO_PENDING_BOOK_CHUNK;

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

    @Override
    public PendingBookChunk findById(Long pendingBookChunkId) {
        return pendingBookChunkJpaRepository.findById(pendingBookChunkId)
                .orElseThrow(() -> new BookException(NO_PENDING_BOOK_CHUNK));
    }

    @Override
    public List<PendingBookChunk> findByIdentificationNumberOrderByIndex(String identificationNumber) {
        return pendingBookChunkJpaRepository.findByIdentificationNumberOrderByIndexAsc(identificationNumber);
    }

}
