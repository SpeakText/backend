package com.speaktext.backend.book.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
public class PendingBookChunk {

    @Id
    @GeneratedValue
    private Long pendingBookChunkId;

    @Column(length = 2000)
    String chunk;

    @Enumerated(EnumType.STRING)
    private PendingBookChunkStatus status;

    public PendingBookChunk(String chunk, PendingBookChunkStatus status) {
        this.chunk = chunk;
        this.status = status;
    }

    public static PendingBookChunk from(String chunk) {
        return new PendingBookChunk(chunk, PendingBookChunkStatus.PENDING);
    }

    public void markAsSent() {
        this.status = PendingBookChunkStatus.SENT;
    }

    public void markAsFailed() {
        this.status = PendingBookChunkStatus.FAILED;
    }

}
