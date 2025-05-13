package com.speaktext.backend.book.script.application.dto;

import java.util.List;

public record CharactersUpdateCommand(
        String identificationNumber,
        List<CharacterUpdateCommand> updatedContent
) {
}
