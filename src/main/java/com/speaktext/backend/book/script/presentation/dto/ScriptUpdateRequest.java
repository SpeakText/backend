package com.speaktext.backend.book.script.presentation.dto;

import com.speaktext.backend.book.script.application.dto.NarrationUpdateCommand;
import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.script.domain.NarrationVoiceType;

public record ScriptUpdateRequest(
        String identificationNumber,
        String voiceType
) {

    public NarrationUpdateCommand toUpdateCommand() {
        NarrationVoiceType type = NarrationVoiceType.from(voiceType);
        return new NarrationUpdateCommand(identificationNumber, type);
    }

}
