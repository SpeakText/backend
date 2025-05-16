package com.speaktext.backend.book.voice.application.event;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.VoiceType;

public record CharacterVoiceGenerationEvent(
        String identificationNumber,
        Long index,
        String speaker,
        String utterance,
        VoiceType voiceType
) {

    public static CharacterVoiceGenerationEvent from(ScriptFragment scriptFragment, VoiceType voiceType) {
        return new CharacterVoiceGenerationEvent(
                scriptFragment.getIdentificationNumber(),
                scriptFragment.getIndex(),
                scriptFragment.getSpeaker(),
                scriptFragment.getUtterance(),
                voiceType
        );
    }

}
