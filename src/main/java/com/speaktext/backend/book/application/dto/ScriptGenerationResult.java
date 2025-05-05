package com.speaktext.backend.book.application.dto;

import com.speaktext.backend.book.domain.ScriptFragment;

import java.util.List;
import java.util.Map;

public record ScriptGenerationResult(

        List<ScriptFragment> fragments,
        Map<String, CharacterInfoDto> updatedCharacters

) {}
