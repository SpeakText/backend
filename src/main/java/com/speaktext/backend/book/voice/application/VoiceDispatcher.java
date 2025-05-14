package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.voice.application.event.CharacterVoiceGenerationEvent;
import com.speaktext.backend.book.voice.application.event.NarrationVoiceGenerationEvent;
import com.speaktext.backend.book.voice.application.publisher.VoiceGenerationEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VoiceDispatcher {

    private final ScriptFragmentRepository scriptFragmentRepository;
    private final VoiceGenerationEventPublisher voiceGenerationEventPublisher;

    public void dispatch(String identificationNumber, Script script) {
        List<ScriptFragment> scriptFragments = scriptFragmentRepository.findByIdentificationNumberOrderByIndex(identificationNumber);
        scriptFragments.forEach(fragment -> {
            Runnable dispatchTask = fragment.isNarration()
                    ? () -> voiceGenerationEventPublisher.publishNarrationEvent(NarrationVoiceGenerationEvent.from(fragment, script.getNarrationVoice()))
                    : () -> voiceGenerationEventPublisher.publishCharacterEvent(CharacterVoiceGenerationEvent.from(fragment));

            dispatchTask.run();
        });
    }

}
