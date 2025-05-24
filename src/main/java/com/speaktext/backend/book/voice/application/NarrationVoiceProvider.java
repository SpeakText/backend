package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.NarrationVoiceType;
import com.speaktext.backend.book.voice.domain.VoiceData;

public interface NarrationVoiceProvider {

    VoiceData generate(String identificationNumber, Long index, String speaker, String text,
                       NarrationVoiceType voice, String fileName, double speed);

}
