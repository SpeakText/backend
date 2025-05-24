package com.speaktext.backend.client.gpt;

import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.script.domain.NarrationVoiceType;
import org.springframework.stereotype.Component;

@Component
public class VoiceTypeGptMapper {

    public static String mapToEngineVoiceName(CharacterVoiceType type) {
        return switch (type) {
            case MALE_LOW -> "ash";          // 남자 낮은 톤
            case MALE_MID -> "ballad";       // 남자 중간 톤
            case MALE_HIGH -> "fable";       // 남자 높은 톤
            case MALE_UNIQUE -> "echo";      // 남자 독특한 톤
            case FEMALE_LOW -> "nova";       // 여자 낮은 톤
            case FEMALE_MID -> "alloy";      // 여자 중간 톤
            case FEMALE_HIGH -> "coral";     // 여자 높은 톤
            case FEMALE_ELDERLY -> "shimmer";// 할머니 목소리
            case NEUTRAL_UNIQUE -> "verse";  // 중성 독특한 목소리
            default -> throw new IllegalArgumentException("Unsupported voice profile: " + type);
        };
    }

    public static String mapToEngineVoiceName(NarrationVoiceType type) {
        return switch (type) {
            case FEMALE_SOFT -> "nova";        // 낮고 부드러운 여성
            case FEMALE_CLEAR -> "alloy";      // 깨끗하고 중립적
            case FEMALE_CASUAL -> "shimmer";   // 밝고 친근한 느낌
            case FEMALE_BRIGHT -> "coral";     // 경쾌하고 밝은 톤
            case FEMALE_CHILD -> "fable";      // 상대적으로 높은 소리
            case MALE_DEEP -> "ash";           // 깊고 낮은 남성
            case MALE_SOFT -> "onyx";          // 부드러운 남성 톤
            case MALE_NEUTRAL -> "ballad";     // 또렷한 중간 톤
            case NO_VOICE -> null;             // 음성 없음 처리
        };
    }

}
