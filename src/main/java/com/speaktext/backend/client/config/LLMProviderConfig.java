package com.speaktext.backend.client.config;

import com.speaktext.backend.book.script.application.implement.ScriptProvider;
import com.speaktext.backend.book.voice.application.VoiceProvider;
import com.speaktext.backend.client.gpt.ScriptAdapter;
import com.speaktext.backend.client.gpt.VoiceAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMProviderConfig {

    @Bean
    public ScriptProvider scriptProvider(
            ScriptAdapter scriptAdapter,
            @Value("${llm.provider}") String provider
    ) {
        return switch (provider.toLowerCase()) {
            case "gpt" -> scriptAdapter;
            default -> throw new IllegalArgumentException("Unsupported LLM provider: " + provider);
        };
    }

    @Bean
    public VoiceProvider voiceProvider(
            VoiceAdapter voiceAdapter,
            @Value("${llm.provider}") String provider
    ) {
        return switch (provider.toLowerCase()) {
            case "gpt" -> voiceAdapter;
            default -> throw new IllegalArgumentException("Unsupported LLM provider: " + provider);
        };
    }

}
