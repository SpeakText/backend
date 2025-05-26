package com.speaktext.backend.book.script.presentation.dto;

import com.speaktext.backend.book.script.domain.ScriptFragment;

import java.util.List;

public record ScriptContentResponse(
        Long readingIndex,
        List<String> contents
) {

    public static ScriptContentResponse fromDomain(Long readingIndex, List<ScriptFragment> scriptFragments) {
        if (scriptFragments == null || scriptFragments.isEmpty()) {
            return new ScriptContentResponse(null, List.of());
        }

        List<String> contents = scriptFragments.stream()
                .map(ScriptFragment::getUtterance)
                .toList();

        return new ScriptContentResponse(readingIndex, contents);
    }

}
