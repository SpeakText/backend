package com.speaktext.backend.client;

import com.speaktext.backend.client.dto.ScriptGenerationRequest;
import com.speaktext.backend.client.dto.ScriptGenerationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ExchangeRateOpenFeign", url = "${llm.api.url}")
public interface ScriptGenerationClient {

    @PostMapping
    ScriptGenerationResponse generate(
            @RequestBody ScriptGenerationRequest request
    );

}
