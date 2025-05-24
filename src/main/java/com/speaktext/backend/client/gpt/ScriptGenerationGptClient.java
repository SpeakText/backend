package com.speaktext.backend.client.gpt;

import com.speaktext.backend.client.config.GptOpenFeignConfig;
import com.speaktext.backend.client.gpt.dto.ScriptGenerationRequest;
import com.speaktext.backend.client.gpt.dto.ScriptGenerationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ScriptGenerationGptClient",
        url = "${llm.api.gpt.base-url}",
        configuration = GptOpenFeignConfig.class
)
public interface ScriptGenerationGptClient {

    @PostMapping(value = "/v1/chat/completions", consumes = "application/json")
    ScriptGenerationResponse generate(
            @RequestBody ScriptGenerationRequest request
    );
}
