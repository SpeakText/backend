package com.speaktext.backend.book.voice.application;


import com.speaktext.backend.book.script.domain.VoiceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterVoiceGenerator {

    private final VoiceProvider voiceProvider;

    public void generateVoice(String identificationNumber, Long index, String speaker, String utterance, VoiceType voiceType, String vibe) {
        String fileName = identificationNumber + "_" + index;
        voiceProvider.generateVoice(
                utterance,
                voiceType.toString(),
                vibe,
                fileName,
                1.0
        );
    }

}
