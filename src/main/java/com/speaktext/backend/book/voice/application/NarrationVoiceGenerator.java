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

        // VoiceData가 null이면 음성 등록하지 않음 (텍스트가 비어있거나 TTS 실패한 경우)
        if (response != null) {
            voiceRegisterHandler.registerVoice(identificationNumber, index, response);
        }
    }

}
