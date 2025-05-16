package com.speaktext.backend.book.infra.listener;

import com.speaktext.backend.book.voice.application.NarrationVoiceGenerator;
import com.speaktext.backend.book.voice.application.event.NarrationVoiceGenerationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NarrationVoiceGenerationWorker {

    private final NarrationVoiceGenerator narrationVoiceGenerator;

    @RabbitListener(queues = "narration.voice.queue", containerFactory = "voiceListenerContainerFactory")
    public void handleNarrationVoiceGeneration(NarrationVoiceGenerationEvent event) {
        narrationVoiceGenerator.generate(
                event.identificationNumber(),
                event.index(),
                event.speaker(),
                event.utterance(),
                event.narrationVoice()
        );
    }

}
