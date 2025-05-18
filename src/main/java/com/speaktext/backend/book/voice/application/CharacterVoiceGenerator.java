package com.speaktext.backend.book.voice.application;


import com.speaktext.backend.book.script.domain.VoiceType;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterVoiceGenerator {

    private final VoiceProvider voiceProvider;
    private final VoiceRegisterService voiceRegisterService;

    public void generateVoice(String identificationNumber, Long index, String speaker, String utterance, VoiceType voiceType, String vibe) {
        String fileName = identificationNumber + "_" + index;
        Response response = voiceProvider.generateVoice(
                utterance,
                voiceType.toString(),
                vibe,
                fileName,
                1.0
        );

        voiceRegisterService.registerVoice(identificationNumber, index, response, fileName);
    }

}
