package com.speaktext.backend.book.script.presentation.dto;

import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.ScriptFragment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ScriptContentResponse(
        Long totalIndex,
        Long readingIndex,
        List<ScriptUtterance> contents
) {

    public static ScriptContentResponse fromDomain(Long readingIndex, List<ScriptFragment> scriptFragments, List<ScriptCharacter> scriptCharacters, Long totalIndex) {
        if (scriptFragments == null || scriptFragments.isEmpty()) {
            return new ScriptContentResponse(totalIndex, null, List.of());
        }

        Map<String, String> characterKeyToName = scriptCharacters.stream()
                .collect(Collectors.toMap(ScriptCharacter::getCharacterKey, ScriptCharacter::getName));

        List<ScriptUtterance> contents = scriptFragments.stream()
                .map(f -> {
                    String speakerName = characterKeyToName.getOrDefault(f.getSpeaker(), f.getSpeaker());
                    return new ScriptUtterance(speakerName, f.getUtterance());
                })
                .toList();

        return new ScriptContentResponse(totalIndex, readingIndex, contents);
    }

    public record ScriptUtterance(
            String speaker,
            String utterance
    ) {}

}