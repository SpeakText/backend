package com.speaktext.backend.book.voice.application.event;

import com.speaktext.backend.book.script.domain.ScriptFragment;

public record CharacterVoiceGenerationEvent(
        String identificationNumber,
        Long index,
        String speaker,
        String utterance
) {

    public static CharacterVoiceGenerationEvent from(ScriptFragment scriptFragment) {
        return new CharacterVoiceGenerationEvent(
                scriptFragment.getIdentificationNumber(),
                scriptFragment.getIndex(),
                scriptFragment.getSpeaker(),
                scriptFragment.getUtterance()
        );
    }

}
