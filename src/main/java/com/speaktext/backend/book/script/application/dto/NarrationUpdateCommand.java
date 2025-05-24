package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.NarrationVoiceType;

public record NarrationUpdateCommand(
        String identificationNumber,
        NarrationVoiceType characterVoiceType
) {
}
