package com.speaktext.backend.book.voice.application.event;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.VoiceType;

public record NarrationVoiceGenerationEvent(
        String identificationNumber,
        Long index,
        String speaker,
        String utterance,
        VoiceType narrationVoice
) {
    public static NarrationVoiceGenerationEvent from(ScriptFragment scriptFragment, VoiceType narrationVoice) {
        return new NarrationVoiceGenerationEvent(
                scriptFragment.getIdentificationNumber(),
                scriptFragment.getIndex(),
                scriptFragment.getSpeaker(),
                scriptFragment.getUtterance(),
                narrationVoice
        );
    }

}
