package com.speaktext.backend.book.script.domain;

public enum VoiceType {
    NO_VOICE,
    ALLOY,
    ASH,
    BALLAD,
    CORAL,
    ECHO,
    FABLE,
    NOVA,
    ONYX,
    SAGE,
    SHIMMER,
    ;

    public static VoiceType from(String value) {
        try {
            return VoiceType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid voice type: " + value);
        }
    }

}
