package com.speaktext.backend.book.domain;

import java.util.List;

public class PendingBookChunks {

    private List<PendingBookChunk> pendingBookChunks;

    private PendingBookChunks(List<PendingBookChunk> pendingBookChunks) {
        this.pendingBookChunks = pendingBookChunks;
    }

    public static PendingBookChunks from(List<String> chunks) {
        List<PendingBookChunk> pendingBookChunks = chunks.stream()
                .map(PendingBookChunk::from)
                .toList();
        return new PendingBookChunks(pendingBookChunks);
    }

    public List<PendingBookChunk> getPendingBookChunks() {
        return pendingBookChunks;
    }

}
