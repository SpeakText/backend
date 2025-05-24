package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.voice.domain.VoiceData;

public interface CharacterVoiceProvider {

    VoiceData generate(String identificationNumber, Long index, String speaker, String text, CharacterVoiceType voice, String fileName, double speed);

}
