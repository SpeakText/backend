package com.speaktext.backend.book.presentation.dto;

import com.speaktext.backend.book.domain.ScriptFragment;

import java.util.List;

public record ScriptModificationRequest(
        String identificationNumber,
        List<FragmentUpdate> updates
) {
    public record FragmentUpdate(
            Long index,
            String speaker,
            String utterance
    ) {}

    public List<ScriptFragment> toScriptFragments() {
        return updates.stream()
                .map(update -> ScriptFragment.builder()
                        .identificationNumber(identificationNumber)
                        .index(update.index())
                        .speaker(update.speaker())
                        .utterance(update.utterance())
                        .build())
                .toList();
    }

}
