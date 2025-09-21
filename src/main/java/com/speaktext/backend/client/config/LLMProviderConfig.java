package com.speaktext.backend.client.config;

import com.speaktext.backend.book.script.application.implement.ScriptProvider;
import com.speaktext.backend.book.voice.application.CharacterVibeGenerator;
import com.speaktext.backend.book.voice.application.CharacterVoiceProvider;
import com.speaktext.backend.book.voice.application.NarrationVoiceProvider;
import com.speaktext.backend.client.clova.NarrationVoiceClovaProvider;
import com.speaktext.backend.client.elevenlabs.ElevenLabsVoiceProvider;
import com.speaktext.backend.client.gpt.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LLMProviderConfig {

    @Bean
    public ScriptProvider scriptProvider(
            ScriptGptProvider scriptGptProvider,
            @Value("${llm.script.provider}") String provider
    ) {
        return switch (provider.toLowerCase()) {
            case "gpt" -> scriptGptProvider;
            default -> throw new IllegalArgumentException("Unsupported LLM provider: " + provider);
        };
    }

    @Bean
    public CharacterVoiceProvider characterVoiceProvider(
            VoiceGenerationGptClient voiceGenerationGptClient,
            CharacterVibeGenerator characterVibeGenerator,
            ElevenLabsVoiceProvider elevenLabsVoiceProvider,
            @Value("${llm.character.provider}") String provider
    ) {
        return switch (provider.toLowerCase()) {
            case "gpt" -> new CharacterVoiceGptProvider(voiceGenerationGptClient, characterVibeGenerator);
            case "elevenlabs" -> elevenLabsVoiceProvider;
            default -> throw new IllegalArgumentException("Unsupported character voice provider: " + provider);
        };
    }

    @Bean
    public NarrationVoiceProvider narrationVoiceProvider(
            RestTemplate restTemplate,
            @Value("${llm.narration.provider}") String provider
    ) {
        return switch (provider.toLowerCase()) {
            case "clova" -> new NarrationVoiceClovaProvider(restTemplate);
            default -> throw new IllegalArgumentException("Unsupported narration voice provider: " + provider);
        };
    }

}
