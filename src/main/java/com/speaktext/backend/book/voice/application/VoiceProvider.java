package com.speaktext.backend.book.voice.application;

import feign.Response;


public interface VoiceProvider {

    Response generateVoice(String text, String voice, String instruction, String fileName, double speed);

}
