package com.speaktext.backend.book.application.publisher;

import com.speaktext.backend.book.application.event.ChunkProcessingEvent;

public interface ChunkMessagePublisher {

    void publish(ChunkProcessingEvent chunk);

}
