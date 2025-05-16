package com.speaktext.backend.client.gpt;

import com.speaktext.backend.client.gpt.dto.VibeGenerationRequest;
import com.speaktext.backend.client.gpt.dto.VibeGenerationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "VibeGenerationClient", url = "${llm.api.base-url}/v1/chat/completions")
public interface VibeGenerationClient {

    @PostMapping
    VibeGenerationResponse generateVibe(
            @RequestBody VibeGenerationRequest request
    );

}