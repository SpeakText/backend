package com.speaktext.backend.book.script.presentation.dto;

import com.speaktext.backend.book.script.application.dto.NarrationUpdateCommand;
import com.speaktext.backend.book.script.domain.VoiceType;

public record ScriptUpdateRequest(
        Long scriptId,
        String voiceType
) {

    public NarrationUpdateCommand toUpdateCommand() {
        VoiceType type = VoiceType.from(voiceType);
        return new NarrationUpdateCommand(scriptId, type);
    }

}
