package com.speaktext.backend.book.script.application.dto;

public record NarrationUpdateResponse(
        Long scriptId,
        String voiceType
) {
}
