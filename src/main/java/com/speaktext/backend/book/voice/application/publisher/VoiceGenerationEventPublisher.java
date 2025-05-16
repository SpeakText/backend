package com.speaktext.backend.book.voice.application.publisher;

import com.speaktext.backend.book.voice.application.event.CharacterVoiceGenerationEvent;
import com.speaktext.backend.book.voice.application.event.NarrationVoiceGenerationEvent;

public interface VoiceGenerationEventPublisher {

    void publishCharacterEvent(CharacterVoiceGenerationEvent event);
    void publishNarrationEvent(NarrationVoiceGenerationEvent event);

}
