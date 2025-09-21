package com.speaktext.backend.book.script.domain;

public enum CharacterVoiceType {
    // ElevenLabs Voice IDs as enum values
    NO_VOICE("NO_VOICE"),

    // 여성 음성들
    RACHEL("21m00Tcm4TlvDq8ikWAM"),              // 여성, 차분하고 나레이션에 적합
    BELLA("EXAVITQu4vr4xnSDxMaL"),               // 여성, 부드럽고 따뜻한 음색
    ELLI("MF3mGyEYCl7XYWbV9V6O"),               // 여성, 감정적이고 명확한 음색
    DOMI("AZnzlk1XvdvUeBnXmlld"),               // 여성, 자신감 있고 강인한 음색
    DOROTHY("ThT5KcBeYPX3keUQqHPh"),            // 여성, 활기차고 젊은 음색
    FREYA("jsCqWAovK2LkecY7zXl4"),              // 여성, 신비롭고 우아한 음색
    GIGI("jBpfuIE2acCO8z3wKNLl"),              // 여성, 밝고 경쾌한 음색
    GLINDA("z9fAnlkpzviPz146aGWa"),             // 여성, 마법적이고 신비한 음색
    GRACE("oWAxZDx7w5VEj9dCyTzz"),              // 여성, 우아하고 세련된 음색
    MATILDA("XrExE9yKIg1WjnnlVkGX"),            // 여성, 성숙하고 지적인 음색
    SERENA("pMsXgVXv3BLzUgSXRplE"),             // 여성, 부드럽고 달콤한 음색
    EMILY("LcfcDJNUP1GQjkzn1xUU"),              // 여성, 활발하고 친근한 음색
    MIMI("zrHiDhphv9ZnVXBqCLjz"),               // 여성, 쾌활하고 귀여운 음색
    NICOLE("piTKgcLEGmPE4e6mEKli"),             // 여성, 세련되고 모던한 음색
    JESSIE("t0jbNlBVZ17f02VDIeMI"),             // 여성, 따뜻하고 친숙한 음색

    // 남성 음성들
    ADAM("pNInz6obpgDQGcFmaJgB"),               // 남성, 깊고 명확한 음색
    ANTONI("ErXwobaYiN019PkySvjV"),             // 남성, 조절되고 중성적인 음색
    ARNOLD("VR6AewLTigWG4xSOukaG"),             // 남성, 진중하고 나레이션 전용
    JOSH("TxGEqnHWrfWFTfGW9XjX"),               // 남성, 깊고 은빛 음색
    SAM("yoZ06aMxZJJ28mfd3POQ"),                // 남성, 라디오 진행자 스타일
    CALLUM("N2lVS1w4EtoT3dr4eOWO"),             // 남성, 친근하고 대화적인 음색
    CHARLIE("IKne3meq5aSn9XLyUdCD"),            // 남성, 젊고 활기찬 음색
    CLYDE("2EiwWnXFnvU5JabPnv8n"),              // 남성, 중년의 성숙한 음색
    DANIEL("onwK4e9ZLuTAKqWW03F9"),             // 남성, 따뜻하고 신뢰감 있는 음색
    DAVE("CYw3kZ02Hs0563khs1Fj"),               // 남성, 편안하고 일상적인 음색
    ETHAN("g5CIjZEefAph4nQFvHAz"),              // 남성, 젊고 에너지 넘치는 음색
    FIN("D38z5RcWu1voky8WS1ja"),                // 남성, 부드럽고 차분한 음색
    GIOVANNI("zcAOhNBS3c14rBihAFp1"),           // 남성, 이탈리아 억양의 매력적 음색
    HARRY("SOYHLrjzK2X1ezoPC6cr"),              // 남성, 영국 억양의 귀족적 음색
    JAMES("ZQe5CZNOzWyzPSCn5a3c"),              // 남성, 신뢰감 있고 전문적인 음색
    JEREMY("bVMeCyTHy58xNoL34h3p"),             // 남성, 젊고 캐주얼한 음색
    JOSEPH("Zlb1dXrM653N07WRdFW3"),             // 남성, 따뜻하고 부드러운 음색
    LIAM("TX3LPaxmHKxFdv7VOQHJ"),               // 남성, 역동적이고 현대적인 음색
    MATTHEW("Yko7PKHZNXotIFUBG7I9"),            // 남성, 성숙하고 안정감 있는 음색
    MICHAEL("flq6f7yk4E4fJM5XTYuZ"),            // 남성, 깊고 카리스마 있는 음색
    PATRICK("ODq5zmih8GrVes37Dizd"),            // 남성, 활발하고 친근한 음색
    RYAN("wViXBPUzp2ZZixB1xQuM"),               // 남성, 시원하고 현대적인 음색
    THOMAS("GBv7mTt0atIp3Br8iCZE"),             // 남성, 안정되고 신뢰감 있는 음색

    // 추가 음성들
    LEONIDAS("YKrm0N1EAM9Bw27j8kuD"),           // 남성, 슬픈 남자
    HARRY_KIM("pb3lVZVjdFWbkhPKlelB"),          // 남성, 날티나는 남자
    HUNMIN("MpbDJfQJUYUnp0i1QvOZ"),             // 남성, 남학생
    ROSA_OH("sf8Bpb1IU97NI9BHSMRf"),            // 여성, 여학생
    SUNNY("4p0HBzAAGyju0nYfNntV");              // 여성, 쓸쓸한 아내

    private final String voiceId;

    CharacterVoiceType(String voiceId) {
        this.voiceId = voiceId;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public static CharacterVoiceType from(String value) {
        // 직접 voice ID로 찾기
        for (CharacterVoiceType type : values()) {
            if (type.voiceId.equals(value)) {
                return type;
            }
        }

        // enum 이름으로 찾기 (기존 호환성)
        try {
            return CharacterVoiceType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid voice type: " + value);
        }
    }

    public boolean isVoiceEnabled() {
        return !this.equals(NO_VOICE);
    }
}
