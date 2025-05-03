package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.event.ScriptGenerationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScriptTransformationService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void generateScript(Long pendingBookId) {
        applicationEventPublisher.publishEvent(new ScriptGenerationEvent(pendingBookId));
    }

}
