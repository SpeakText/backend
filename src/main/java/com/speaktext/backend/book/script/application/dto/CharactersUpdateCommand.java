package com.speaktext.backend.book.script.application.dto;

import java.util.List;

public record CharactersUpdateCommand(
        Long scriptId,
        List<CharacterUpdateCommand> updatedContent
) {
}
