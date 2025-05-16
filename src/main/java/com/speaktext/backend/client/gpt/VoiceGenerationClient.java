package com.speaktext.backend.client.gpt;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "VoiceGenerationClient", url = "${llm.api.base-url}/v1/audio/speech")
public interface VoiceGenerationClient {

    @PostMapping
    Response generateSpeech(
            @RequestBody Map<String, Object> request
    );

}