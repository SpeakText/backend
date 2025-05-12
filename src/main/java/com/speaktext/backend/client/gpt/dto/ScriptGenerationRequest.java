package com.speaktext.backend.client.gpt.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ScriptGenerationRequest {

    private String model;
    private List<Message> messages;
    private double temperature;

    @Data
    @Builder
    public static class Message {
        private String role;
        private String content;
    }

}
