package com.speaktext.backend.client.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// FeignClient 대신 RestTemplate 사용으로 더 이상 필요없음
// @Configuration
public class ElevenLabsConfig {

    // @Value("${llm.api.elevenlabs.key}")
    // private String apiKey;

    // @Bean
    // public RequestInterceptor elevenLabsRequestInterceptor() {
    //     return new RequestInterceptor() {
    //         @Override
    //         public void apply(RequestTemplate template) {
    //             System.out.println("헤더 적용 전 - API Key: " + (apiKey != null ? apiKey.substring(0, Math.min(10, apiKey.length())) + "..." : "null"));
    //             System.out.println("요청 URL: " + template.url());
    //             System.out.println("요청 메서드: " + template.method());

    //             // ElevenLabs API 문서에 따른 올바른 헤더 형식 시도
    //             template.header("xi-api-key", apiKey);  // 표준 형식
    //             // template.header("Authorization", "Bearer " + apiKey);  // 대안
    //             template.header("Content-Type", "application/json");

    //             System.out.println("적용된 헤더들: " + template.headers());
    //         }
    //     };
    // }
}