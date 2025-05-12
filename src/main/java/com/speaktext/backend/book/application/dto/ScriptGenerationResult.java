package com.speaktext.backend.book.application.dto;

import com.speaktext.backend.book.domain.ScriptFragment;

import java.util.List;

public record ScriptGenerationResult(

        List<ScriptFragment> fragments,
        List<CharacterDto> updatedCharacters

) {}
