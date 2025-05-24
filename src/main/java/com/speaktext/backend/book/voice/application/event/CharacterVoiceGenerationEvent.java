package com.speaktext.backend.book.voice.application.event;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.CharacterVoiceType;

public record CharacterVoiceGenerationEvent(
        String identificationNumber,
        Long index,
        String speaker,
        String utterance,
        CharacterVoiceType characterVoiceType
) {

    public static CharacterVoiceGenerationEvent from(ScriptFragment scriptFragment, CharacterVoiceType characterVoiceType) {
        return new CharacterVoiceGenerationEvent(
                scriptFragment.getIdentificationNumber(),
                scriptFragment.getIndex(),
                scriptFragment.getSpeaker(),
                scriptFragment.getUtterance(),
                characterVoiceType
        );
    }

}
