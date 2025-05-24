package com.speaktext.backend.client.gpt;

import com.speaktext.backend.client.config.GptOpenFeignConfig;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "VoiceGenerationGptClient",
        url = "${llm.api.gpt.base-url}",
        configuration = GptOpenFeignConfig.class
)
public interface VoiceGenerationGptClient {

    @PostMapping(value = "/v1/audio/speech", consumes = "application/json")
    Response generateSpeech(@RequestBody Map<String, Object> request);

}
