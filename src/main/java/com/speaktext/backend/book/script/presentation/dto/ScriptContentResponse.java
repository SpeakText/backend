package com.speaktext.backend.book.script.presentation.dto;

import com.speaktext.backend.book.script.domain.ScriptFragment;

import java.util.List;

public record ScriptContentResponse(
        Long readingIndex,
        List<ScriptUtterance> contents
) {

    public static ScriptContentResponse fromDomain(Long readingIndex, List<ScriptFragment> scriptFragments) {
        if (scriptFragments == null || scriptFragments.isEmpty()) {
            return new ScriptContentResponse(null, List.of());
        }

        List<ScriptUtterance> contents = scriptFragments.stream()
                .map(f -> new ScriptUtterance(f.getSpeaker(), f.getUtterance()))
                .toList();

        return new ScriptContentResponse(readingIndex, contents);
    }

    public record ScriptUtterance(
            String speaker,
            String utterance
    ) {}

}