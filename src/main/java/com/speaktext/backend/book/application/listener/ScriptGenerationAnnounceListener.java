package com.speaktext.backend.book.application.listener;

import com.speaktext.backend.book.application.ScriptTransformationService;
import com.speaktext.backend.book.application.event.ScriptGenerationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScriptGenerationAnnounceListener {

    private final ScriptTransformationService scriptTransformationService;

    @EventListener
    public void handleScriptGenerationEvent(ScriptGenerationEvent event) {
        Long pendingBookId = event.pendingBookId();
        scriptTransformationService.prepareScriptGeneration(pendingBookId);
    }

}
