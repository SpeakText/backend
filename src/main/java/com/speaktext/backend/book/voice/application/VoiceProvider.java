package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.VoiceType;
import feign.Response;


public interface VoiceProvider {

    Response generateCharacterVoice(String identificationNumber, Long index, String speaker, String text, VoiceType voice, String fileName, double speed);
    Response generateNarrationVoice(String identificationNumber, Long index, String speaker, String text, VoiceType voice, String fileName, double speed);

}
