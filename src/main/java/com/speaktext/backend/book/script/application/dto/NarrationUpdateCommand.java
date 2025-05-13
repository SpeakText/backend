package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.VoiceType;

public record NarrationUpdateCommand(
        Long scriptId,
        VoiceType voiceType
) {
}
