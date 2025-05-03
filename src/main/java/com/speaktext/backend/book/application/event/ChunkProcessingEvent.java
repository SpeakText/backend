package com.speaktext.backend.book.application.event;

public record ChunkProcessingEvent(
        Long pendingBookChunkId
) {
}
