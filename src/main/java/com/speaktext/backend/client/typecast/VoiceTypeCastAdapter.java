package com.speaktext.backend.client.typecast;

import com.speaktext.backend.book.script.domain.VoiceType;
import com.speaktext.backend.book.voice.application.VoiceProvider;
import feign.Response;

public class VoiceTypeCastAdapter implements VoiceProvider {

    @Override
    public Response generateCharacterVoice(String identificationNumber, Long index, String speaker, String text, VoiceType voice, String fileName, double speed) {
        return null;
    }

    @Override
    public Response generateNarrationVoice(String identificationNumber, Long index, String speaker, String text, VoiceType voice, String fileName, double speed) {
        return null;
    }

}
