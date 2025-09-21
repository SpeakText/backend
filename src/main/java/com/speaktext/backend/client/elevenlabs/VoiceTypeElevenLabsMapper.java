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
}