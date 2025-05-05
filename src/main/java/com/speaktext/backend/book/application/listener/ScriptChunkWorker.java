package com.speaktext.backend.book.application.listener;

import com.speaktext.backend.book.application.ScriptGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScriptChunkWorker {

    private final ScriptGenerator scriptGenerator;

    @RabbitListener(queues = "script.chunk.queue")
    public void handleChunk(String chunkIdStr) {
        try {
            Long chunkId = Long.parseLong(chunkIdStr);
            scriptGenerator.generate(chunkId);
        } catch (NumberFormatException e) {
            System.err.println("유효하지 않은 chunkId 수신: " + chunkIdStr);
        }
    }

}
