package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptCharacter;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.repository.CharacterRepository;
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
    private final CharacterRepository characterRepository;

    public void dispatch(String identificationNumber, Script script) {
        List<ScriptFragment> scriptFragments = scriptFragmentRepository.findByIdentificationNumberOrderByIndex(identificationNumber);
        scriptFragments.forEach(fragment -> {
            if (fragment.isNarration()) {
                NarrationVoiceGenerationEvent narrationEvent = NarrationVoiceGenerationEvent.from(fragment, script.getNarrationVoice());
                voiceGenerationEventPublisher.publishNarrationEvent(narrationEvent);
            } else {
                ScriptCharacter character = characterRepository.findByScriptAndCharacterKey(script, fragment.getSpeaker())
                        .orElseThrow(() -> new IllegalArgumentException("Character not found for speaker: " + fragment.getSpeaker()));

                CharacterVoiceGenerationEvent characterEvent = CharacterVoiceGenerationEvent.from(fragment, character.getVoiceType());
                voiceGenerationEventPublisher.publishCharacterEvent(characterEvent);
            }
        });
    }

}
