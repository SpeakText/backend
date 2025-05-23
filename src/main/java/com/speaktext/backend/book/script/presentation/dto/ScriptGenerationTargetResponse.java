package com.speaktext.backend.book.script.presentation.dto;

public record ScriptGenerationTargetResponse(
        String title,
        Long authorId,
        String identificationNumber,
        boolean scriptStatus
) {
}
