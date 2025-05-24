package com.speaktext.backend.book.script.domain;

public enum CharacterVoiceType {
    NO_VOICE,
    MALE_LOW,
    MALE_MID,
    MALE_HIGH,
    MALE_UNIQUE,
    FEMALE_LOW,
    FEMALE_MID,
    FEMALE_HIGH,
    FEMALE_ELDERLY,
    NEUTRAL_UNIQUE,
    ;

    public static CharacterVoiceType from(String value) {
        try {
            return CharacterVoiceType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid voice type: " + value);
        }
    }

}
