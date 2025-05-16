package com.speaktext.backend.book.infra.listener;

import com.speaktext.backend.book.voice.application.event.CharacterVoiceGenerationEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class CharacterVoiceGenerationWorker {

    @RabbitListener(queues = "character.voice.queue", containerFactory = "voiceListenerContainerFactory")
    public void handleCharacterVoiceGeneration(CharacterVoiceGenerationEvent event) {

    }

}
