package com.speaktext.backend.book.voice.application;


import com.speaktext.backend.book.script.domain.CharacterVoiceType;
import com.speaktext.backend.book.voice.domain.VoiceData;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterVoiceGenerator {

    private final CharacterVoiceProvider characterVoiceProvider;
    private final VoiceRegisterHandler voiceRegisterHandler;

    public void generateVoice(String identificationNumber, Long index, String speaker, String utterance, CharacterVoiceType characterVoiceType) {
        String fileName = identificationNumber + "_" + index;
        VoiceData response = characterVoiceProvider.generate(
                identificationNumber,
                index,
                speaker,
                utterance,
                characterVoiceType,
                fileName,
                1.0
        );

        voiceRegisterHandler.registerVoice(identificationNumber, index, response);
    }

}
