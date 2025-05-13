package com.speaktext.backend.book.script.presentation.dto;

public record CharacterResponse(
        String characterKey,
        String name,
        String voiceType
) {
}
