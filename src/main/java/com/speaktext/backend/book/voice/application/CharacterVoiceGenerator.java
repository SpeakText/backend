package com.speaktext.backend.book.voice.application;


import com.speaktext.backend.book.script.domain.VoiceType;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterVoiceGenerator {

    private final VoiceProvider voiceProvider;
    private final VoiceRegisterHandler voiceRegisterHandler;

    public void generateVoice(String identificationNumber, Long index, String speaker, String utterance, VoiceType voiceType) {
        String fileName = identificationNumber + "_" + index;
        Response response = voiceProvider.generateCharacterVoice(
                identificationNumber,
                index,
                speaker,
                utterance,
                voiceType,
                fileName,
                1.0
        );

        voiceRegisterHandler.registerVoice(identificationNumber, index, response, fileName);
    }

}
