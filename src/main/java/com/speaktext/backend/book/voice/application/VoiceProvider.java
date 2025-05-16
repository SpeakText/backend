package com.speaktext.backend.book.voice.application;

import java.nio.file.Path;

public interface VoiceProvider {

    Path generateVoice(String text, String voice, String instruction, String fileName);

}
