package com.speaktext.backend.book.domain;

import java.util.List;

public class PendingBookChunks {

    private List<PendingBookChunk> pendingBookChunks;

    private PendingBookChunks(List<PendingBookChunk> pendingBookChunks) {
        this.pendingBookChunks = pendingBookChunks;
    }

    public static PendingBookChunks of(List<String> chunks, String identificationNumber) {
        List<PendingBookChunk> pendingBookChunks = chunks.stream()
                .map(chunk -> PendingBookChunk.of(chunk, identificationNumber))
                .toList();
        return new PendingBookChunks(pendingBookChunks);
    }

    public List<PendingBookChunk> getPendingBookChunks() {
        return pendingBookChunks;
    }

    public int getNumberOfPendingBookChunks() {
        return pendingBookChunks.size();
    }

}
