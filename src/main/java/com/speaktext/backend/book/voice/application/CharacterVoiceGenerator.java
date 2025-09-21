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

        // VoiceData가 null이면 음성 등록하지 않음 (텍스트가 비어있거나 TTS 실패한 경우)
        if (response != null) {
            voiceRegisterHandler.registerVoice(identificationNumber, index, response);
        }
    }

}
