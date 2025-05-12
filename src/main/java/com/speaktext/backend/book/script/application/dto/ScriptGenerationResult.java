package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.ScriptFragment;

import java.util.List;

public record ScriptGenerationResult(

        List<ScriptFragment> fragments,
        List<CharacterDto> updatedCharacters

) {}
