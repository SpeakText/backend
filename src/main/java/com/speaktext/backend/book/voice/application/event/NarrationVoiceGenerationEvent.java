package com.speaktext.backend.book.voice.application.event;

import com.speaktext.backend.book.script.domain.NarrationVoiceType;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.CharacterVoiceType;

public record NarrationVoiceGenerationEvent(
        String identificationNumber,
        Long index,
        String speaker,
        String utterance,
        NarrationVoiceType narrationVoice
) {
    public static NarrationVoiceGenerationEvent from(ScriptFragment scriptFragment, NarrationVoiceType narrationVoice) {
        return new NarrationVoiceGenerationEvent(
                scriptFragment.getIdentificationNumber(),
                scriptFragment.getIndex(),
                scriptFragment.getSpeaker(),
                scriptFragment.getUtterance(),
                narrationVoice
        );
    }

}
