package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.CharacterVoiceType;

public record CharacterUpdateCommand(
        String characterKey,
        String name,
        CharacterVoiceType characterVoiceType
) {
}
