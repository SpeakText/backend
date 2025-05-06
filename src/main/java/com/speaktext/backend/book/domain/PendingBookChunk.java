package com.speaktext.backend.book.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class PendingBookChunk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pendingBookChunkId;

    @Column(length = 2000)
    String chunk;

    private String identificationNumber;

    @Enumerated(EnumType.STRING)
    private PendingBookChunkStatus status;

    @Column(name = "chunk_index")
    private Long index;

    public PendingBookChunk(String chunk, PendingBookChunkStatus status, String identificationNumber, Long index) {
        this.chunk = chunk;
        this.status = status;
        this.identificationNumber = identificationNumber;
        this.index = index;
    }

    public static PendingBookChunk of(String chunk, String identificationNumber, Long index) {
        return new PendingBookChunk(chunk, PendingBookChunkStatus.PENDING, identificationNumber, index);
    }

    public void markAsSent() {
        this.status = PendingBookChunkStatus.SENT;
    }

    public void markAsFailed() {
        this.status = PendingBookChunkStatus.FAILED;
    }

}
