package com.speaktext.backend.client.config;

import com.speaktext.backend.book.application.LLMProvider;
import com.speaktext.backend.client.gpt.GptAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LLMProviderConfig {

    @Bean
    public LLMProvider llmProvider(
            GptAdapter gptAdapter,
            @Value("${llm.provider}") String provider
    ) {
        return switch (provider.toLowerCase()) {
            case "gpt" -> gptAdapter;
            default -> throw new IllegalArgumentException("Unsupported LLM provider: " + provider);
        };
    }

}
