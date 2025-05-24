package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.voice.domain.VoiceData;

public interface VoiceRegisterHandler {

    void registerVoice(String identificationNumber, Long index, VoiceData response);
}
