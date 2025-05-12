package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.ScriptFragment;

public record ScriptResponse(
        String speaker,
        String utterance,
        Long index
) {

    public static ScriptResponse from(ScriptFragment scriptFragment) {
        return new ScriptResponse(scriptFragment.getSpeaker(), scriptFragment.getUtterance(), scriptFragment.getIndex());
    }

}
