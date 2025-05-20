package com.speaktext.backend.client.config;

import com.speaktext.backend.book.script.application.implement.ScriptProvider;
import com.speaktext.backend.book.voice.application.VoiceProvider;
import com.speaktext.backend.client.gpt.ScriptGptAdapter;
import com.speaktext.backend.client.gpt.VoiceGptAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMProviderConfig {

    @Bean
    public ScriptProvider scriptProvider(
            ScriptGptAdapter scriptGptAdapter,
            @Value("${llm.provider}") String provider
    ) {
        return switch (provider.toLowerCase()) {
            case "gpt" -> scriptGptAdapter;
            default -> throw new IllegalArgumentException("Unsupported LLM provider: " + provider);
        };
    }

    @Bean
    public VoiceProvider voiceProvider(
            VoiceGptAdapter voiceGptAdapter,
            @Value("${llm.provider}") String provider
    ) {
        return switch (provider.toLowerCase()) {
            case "gpt" -> voiceGptAdapter;
            default -> throw new IllegalArgumentException("Unsupported LLM provider: " + provider);
        };
    }

}
