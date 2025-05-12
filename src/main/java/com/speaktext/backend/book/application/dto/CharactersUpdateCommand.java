package com.speaktext.backend.book.application.dto;

import java.util.List;

public record CharactersUpdateCommand(
        Long scriptId,
        List<CharacterUpdateCommand> updatedContent
) {
}
