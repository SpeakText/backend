package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBookChunk;

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

}
