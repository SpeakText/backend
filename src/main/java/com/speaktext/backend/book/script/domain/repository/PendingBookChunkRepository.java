package com.speaktext.backend.book.script.domain.repository;

import com.speaktext.backend.book.script.domain.PendingBookChunk;

import java.util.List;

public interface PendingBookChunkRepository {

    void save(PendingBookChunk pendingBookChunk);
    void saveAll(List<PendingBookChunk> pendingBookChunks);
    PendingBookChunk findById(Long pendingBookChunkId);
    List<PendingBookChunk> findByIdentificationNumberOrderByIndex(String identificationNumber);

}

