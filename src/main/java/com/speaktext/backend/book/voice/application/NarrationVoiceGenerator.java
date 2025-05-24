package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.NarrationVoiceType;
import com.speaktext.backend.book.voice.domain.VoiceData;
import feign.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NarrationVoiceGenerator {

    private final NarrationVoiceProvider voiceProvider;
    private final VoiceRegisterHandler voiceRegisterHandler;

    public void generate(String identificationNumber, Long index, String speaker, String utterance, NarrationVoiceType narrationVoiceType) {
        String fileName = identificationNumber + "_" + index;

        VoiceData response = voiceProvider.generate(
                identificationNumber,
                index,
                speaker,
                utterance,
                narrationVoiceType,
                fileName,
                1.0
        );

        voiceRegisterHandler.registerVoice(identificationNumber, index, response);
    }

}
