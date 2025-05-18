package com.speaktext.backend.book.voice.application;

import feign.Response;

public interface VoiceRegisterService {

    void registerVoice(String identificationNumber, Long index, Response response, String fileName);
}
