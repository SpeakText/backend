package com.speaktext.backend.book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PendingBookChunk {

    @Id
    @GeneratedValue
    private Long pendingBookChunkId;

    @Column(length = 2000)
    String chunk;

    private String identificationNumber;

    @Enumerated(EnumType.STRING)
    private PendingBookChunkStatus status;

    public PendingBookChunk(String chunk, PendingBookChunkStatus status, String identificationNumber) {
        this.chunk = chunk;
        this.status = status;
        this.identificationNumber = identificationNumber;
    }

    public static PendingBookChunk of(String chunk, String identificationNumber) {
        return new PendingBookChunk(chunk, PendingBookChunkStatus.PENDING, identificationNumber);
    }

    public void markAsSent() {
        this.status = PendingBookChunkStatus.SENT;
    }

    public void markAsFailed() {
        this.status = PendingBookChunkStatus.FAILED;
    }

}
