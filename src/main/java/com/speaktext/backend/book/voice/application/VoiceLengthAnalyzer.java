package com.speaktext.backend.book.voice.application;

import java.io.File;

public interface VoiceLengthAnalyzer {

    Long getVoiceLength(File mp3File);
}
