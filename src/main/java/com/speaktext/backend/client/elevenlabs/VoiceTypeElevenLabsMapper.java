package com.speaktext.backend.client.elevenlabs;

import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.script.domain.NarrationVoiceType;
import org.springframework.stereotype.Component;

@Component
public class VoiceTypeElevenLabsMapper {

    public static String mapToElevenLabsVoiceId(CharacterVoiceType type) {
        if (type == CharacterVoiceType.NO_VOICE) {
            return null;
        }
        return type.getVoiceId();
    }

    public static String mapToElevenLabsVoiceId(NarrationVoiceType type) {
        return switch (type) {
            case FEMALE_SOFT -> "ThT5KcBeYPX3keUQqHPh";     // Dorothy - 낮고 부드러운 여성
            case FEMALE_CLEAR -> "XB0fDUnXU5powFXDhCwa";    // Charlotte - 깨끗하고 중립적
            case FEMALE_CASUAL -> "pFZP5JQG7iQjIQuC4Bku";   // Lily - 밝고 친근한 느낌
            case FEMALE_BRIGHT -> "pFZP5JQG7iQjIQuC4Bku";   // Lily - 경쾌하고 밝은 톤
            case FEMALE_CHILD -> "pFZP5JQG7iQjIQuC4Bku";    // Lily - 상대적으로 높은 소리
            case MALE_DEEP -> "bVMeCyTHy58xNoL34h3p";       // Jeremy - 깊고 낮은 남성
            case MALE_SOFT -> "N2lVS1w4EtoT3dr4eOWO";       // Callum - 부드러운 남성 톤
            case MALE_NEUTRAL -> "N2lVS1w4EtoT3dr4eOWO";     // Callum - 또렷한 중간 톤
            case NO_VOICE -> null;                           // 음성 없음 처리
        };
    }
}