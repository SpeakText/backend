package com.speaktext.backend.book.script.domain.event;

public record ScriptGenerationEvent(
        Long pendingBookId
) {
}
