package com.speaktext.backend.client.dto;

import lombok.Data;

@Data
public class ScriptGenerationResponse {

    private Choice[] choices;

    @Data
    public static class Choice {
        private Message message;
    }

    @Data
    public static class Message {
        private String role;
        private String content;
    }

}
