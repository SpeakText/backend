package com.speaktext.backend.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {
        "com.speaktext.backend.client",
        "com.speaktext.backend.book"
})
class OpenFeignConfig {

    @Value("${llm.api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor openAiAuthInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + apiKey);
            requestTemplate.header("Content-Type", "application/json");
        };
    }

}
