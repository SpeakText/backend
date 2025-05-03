package com.speaktext.backend.book.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class PendingBookChunk {

    @Id
    @GeneratedValue
    private Long pendingBookChunkId;

    @Column(length = 2000)
    String chunk;

    public PendingBookChunk(String chunk) {
        this.chunk = chunk;
    }

    public static PendingBookChunk from(String chunk) {
        return new PendingBookChunk(chunk);
    }

}
