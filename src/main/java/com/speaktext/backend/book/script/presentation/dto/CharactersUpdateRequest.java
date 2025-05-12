package com.speaktext.backend.book.script.presentation.dto;

import com.speaktext.backend.book.script.application.dto.CharactersUpdateCommand;
import com.speaktext.backend.book.script.application.dto.CharacterUpdateCommand;
import com.speaktext.backend.book.script.domain.VoiceType;

import java.util.List;

public record CharactersUpdateRequest(
        Long scriptId,
        List<CharacterUpdateRequest> characters
) {

    public CharactersUpdateCommand toUpdateCommand() {
        List<CharacterUpdateCommand> commands = characters.stream()
                .map(c -> new CharacterUpdateCommand(
                        c.characterKey(),
                        c.name(),
                        VoiceType.valueOf(c.voiceType().toUpperCase())
                ))
                .toList();

        return new CharactersUpdateCommand(scriptId, commands);
    }

    public record CharacterUpdateRequest(
            String characterKey,
            String name,
            String voiceType
    ) {}

}