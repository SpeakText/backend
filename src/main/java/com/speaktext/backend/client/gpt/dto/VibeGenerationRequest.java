package com.speaktext.backend.client.gpt.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VibeGenerationRequest {

    private String model;
    private Double temperature;
    private List<Message> messages;

    @Data
    @Builder
    public static class Message {
        private String role;
        private String content;
    }

}