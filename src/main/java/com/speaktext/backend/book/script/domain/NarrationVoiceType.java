package com.speaktext.backend.book.script.domain;

public enum NarrationVoiceType {
    NO_VOICE,
    FEMALE_SOFT,
    FEMALE_CLEAR,
    FEMALE_CASUAL,
    FEMALE_BRIGHT,
    FEMALE_CHILD,
    MALE_DEEP,
    MALE_SOFT,
    MALE_NEUTRAL,
    ;

    public static NarrationVoiceType from(String value) {
        try {
            return NarrationVoiceType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid voice type: " + value);
        }
    }

}
