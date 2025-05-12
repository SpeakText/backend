package com.speaktext.backend.book.application.dto;

import com.speaktext.backend.book.domain.VoiceType;

public record CharacterUpdateCommand(
        String characterKey,
        String name,
        VoiceType voiceType
) {
}
