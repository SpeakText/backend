package com.speaktext.backend.client.gpt;

import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.script.domain.NarrationVoiceType;
import org.springframework.stereotype.Component;

@Component
public class VoiceTypeGptMapper {

    public static String mapToEngineVoiceName(CharacterVoiceType type) {
        // GPT TTS는 현재 사용하지 않으므로 기본값 반환
        // ElevenLabs로 전환했으나 GPT 호환성을 위해 유지
        return switch (type) {
            case NO_VOICE -> null;
            case RACHEL, BELLA, ELLI, DOMI, DOROTHY, FREYA, GIGI, GLINDA, GRACE,
                 MATILDA, SERENA, EMILY, MIMI, NICOLE, JESSIE -> "alloy";  // 여성 기본값
            case ADAM, ANTONI, ARNOLD, JOSH, SAM, CALLUM, CHARLIE, CLYDE,
                 DANIEL, DAVE, ETHAN, FIN, GIOVANNI, HARRY, JAMES, JEREMY,
                 JOSEPH, LIAM, MATTHEW, MICHAEL, PATRICK, RYAN, THOMAS -> "ballad";  // 남성 기본값
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
