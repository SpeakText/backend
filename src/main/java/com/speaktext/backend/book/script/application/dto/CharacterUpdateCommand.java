package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.VoiceType;

public record CharacterUpdateCommand(
        String characterKey,
        String name,
        VoiceType voiceType
) {
}
