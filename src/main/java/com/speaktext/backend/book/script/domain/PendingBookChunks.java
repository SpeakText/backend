package com.speaktext.backend.book.script.domain;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class PendingBookChunks {

    private List<PendingBookChunk> pendingBookChunks;

    private PendingBookChunks(List<PendingBookChunk> pendingBookChunks) {
        this.pendingBookChunks = pendingBookChunks;
    }

    public static PendingBookChunks of(List<String> chunks, String identificationNumber) {
        AtomicLong indexCounter = new AtomicLong(0);
        List<PendingBookChunk> pendingBookChunks = chunks.stream()
                .map(chunk -> PendingBookChunk.of(chunk, identificationNumber, indexCounter.getAndIncrement()))
                .toList();
        return new PendingBookChunks(pendingBookChunks);
    }

    public List<PendingBookChunk> getPendingBookChunks() {
        return pendingBookChunks;
    }

    public int getNumberOfPendingBookChunks() {
        return pendingBookChunks.size();
    }

    public List<PendingBookChunk> getPendingBookChunkNotSent() {
        return pendingBookChunks.stream()
                .filter(pendingBookChunk -> pendingBookChunk.getStatus() != PendingBookChunkStatus.SENT)
                .toList();
    }

}
