package com.speaktext.backend.book.infra.publisher.impl;

import com.speaktext.backend.book.script.domain.event.ChunkProcessingEvent;
import com.speaktext.backend.book.script.application.publisher.ChunkMessagePublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChunkMessagePublisherImpl implements ChunkMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(ChunkProcessingEvent event) {
        rabbitTemplate.convertAndSend(
                "script.chunk.exchange",
                "script.chunk.routingKey",
                String.valueOf(event.pendingBookChunkId())
        );
    }

}
