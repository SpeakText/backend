package com.speaktext.backend.book.application.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class ScriptChunkWorker {

    @RabbitListener(queues = "script.chunk.queue")
    public void handleChunk(String chunkId) {

    }

}
