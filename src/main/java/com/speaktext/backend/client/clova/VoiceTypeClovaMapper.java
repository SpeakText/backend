package com.speaktext.backend.client.clova;

import com.speaktext.backend.book.script.domain.NarrationVoiceType;

import java.util.Map;

public class VoiceTypeClovaMapper {

    private static final Map<NarrationVoiceType, String> clovaVoiceMap = Map.of(
            NarrationVoiceType.FEMALE_SOFT, "vara",
            NarrationVoiceType.FEMALE_CLEAR, "vmikyung",
            NarrationVoiceType.FEMALE_CASUAL, "vhyeri",
            NarrationVoiceType.FEMALE_BRIGHT, "vyuna",
            NarrationVoiceType.FEMALE_CHILD, "vdain",
            NarrationVoiceType.MALE_DEEP, "vdaeseong",
            NarrationVoiceType.MALE_SOFT, "vian",
            NarrationVoiceType.MALE_NEUTRAL, "vdonghyun"
    );

    public static String mapToEngineVoiceName(NarrationVoiceType type) {
        String voice = clovaVoiceMap.get(type);
        if (voice == null) {
            throw new IllegalArgumentException("Unsupported Clova narration voice type: " + type);
        }
        return voice;
    }

}
