package com.speaktext.backend.book.script.application.publisher;

import com.speaktext.backend.book.script.domain.event.ChunkProcessingEvent;

public interface ChunkMessagePublisher {

    void publish(ChunkProcessingEvent chunk);

}
