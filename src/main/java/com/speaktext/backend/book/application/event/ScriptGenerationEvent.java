package com.speaktext.backend.book.application.event;

public record ScriptGenerationEvent(
        Long pendingBookId
) {
}
