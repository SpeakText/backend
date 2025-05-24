package com.speaktext.backend.client.gpt;

import com.speaktext.backend.client.config.GptOpenFeignConfig;
import com.speaktext.backend.client.gpt.dto.VibeGenerationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "VibeGenerationClient",
        url = "${llm.api.gpt.base-url}",
        configuration = GptOpenFeignConfig.class
)
public interface VibeGenerationGptClient {

    @PostMapping(value = "/v1/chat/completions", consumes = "application/json")
    String generateVibeRaw(@RequestBody VibeGenerationRequest request);

}
