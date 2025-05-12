package com.speaktext.backend.book.infra.listener;

import com.speaktext.backend.book.script.application.ScriptProcessingPipeline;
import com.speaktext.backend.book.script.domain.event.ScriptGenerationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScriptGenerationAnnounceListener {

    private final ScriptProcessingPipeline pipeline;

    @EventListener
    public void handleScriptGenerationEvent(ScriptGenerationEvent event) {
        pipeline.prepareScriptGeneration(event.pendingBookId());
    }

}
