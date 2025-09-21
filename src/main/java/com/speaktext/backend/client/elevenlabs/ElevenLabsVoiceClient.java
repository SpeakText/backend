package com.speaktext.backend.client.elevenlabs;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ElevenLabsVoiceClient {

    @Value("${llm.api.elevenlabs.base-url}")
    private String baseUrl;

    @Value("${llm.api.elevenlabs.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ResponseEntity<byte[]> generateSpeech(String voiceId, Map<String, Object> request) {
        try {
            String url = baseUrl + "/v1/text-to-speech/" + voiceId + "?output_format=mp3_44100_128";

            HttpHeaders headers = new HttpHeaders();
            headers.set("xi-api-key", apiKey);
            headers.set("Content-Type", "application/json");

            String requestBody = objectMapper.writeValueAsString(request);
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            System.out.println("ElevenLabs 직접 요청:");
            System.out.println("URL: " + url);
            System.out.println("Headers: " + headers);
            System.out.println("Body: " + requestBody);

            return restTemplate.exchange(url, HttpMethod.POST, entity, byte[].class);

        } catch (Exception e) {
            throw new RuntimeException("ElevenLabs HTTP 요청 실패", e);
        }
    }
}