package com.speaktext.backend.book.infra.listener;

import com.speaktext.backend.book.voice.application.CharacterVibeGenerator;
import com.speaktext.backend.book.voice.application.CharacterVoiceGenerator;
import com.speaktext.backend.book.voice.application.event.CharacterVoiceGenerationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterVoiceGenerationWorker {

    private final CharacterVibeGenerator characterVibeGenerator;
    private final CharacterVoiceGenerator characterVoiceGenerator;

    @RabbitListener(queues = "character.voice.queue", containerFactory = "voiceListenerContainerFactory")
    public void handleCharacterVoiceGeneration(CharacterVoiceGenerationEvent event) {
        String vibe = characterVibeGenerator.generateVibe(
                event.identificationNumber(),
                event.index(),
                event.speaker()
        );

        log.info("Vibe: {}", vibe);

        characterVoiceGenerator.generateVoice(
                event.identificationNumber(),
                event.index(),
                event.speaker(),
                event.utterance(),
                event.voiceType(),
                vibe
        );
    }

}
