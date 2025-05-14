package com.speaktext.backend.book.infra.publisher.impl;

import com.speaktext.backend.book.voice.application.event.CharacterVoiceGenerationEvent;
import com.speaktext.backend.book.voice.application.event.NarrationVoiceGenerationEvent;
import com.speaktext.backend.book.voice.application.publisher.VoiceGenerationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoiceGenerationEventPublisherImpl implements VoiceGenerationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishCharacterEvent(CharacterVoiceGenerationEvent event) {
        rabbitTemplate.convertAndSend(
                "character.voice.exchange",
                "character.voice.routingKey",
                event
        );
    }

    @Override
    public void publishNarrationEvent(NarrationVoiceGenerationEvent event) {
        rabbitTemplate.convertAndSend(
                "narration.voice.exchange",
                "narration.voice.routingKey",
                event
        );
    }

}
