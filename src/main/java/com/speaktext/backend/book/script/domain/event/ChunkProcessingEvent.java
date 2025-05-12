package com.speaktext.backend.book.script.domain.event;

public record ChunkProcessingEvent(
        Long pendingBookChunkId
) {
}
