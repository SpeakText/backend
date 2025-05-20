package com.speaktext.backend.book.voice.domain;

public class CumulativeVoiceDuration {

    private final String cumulativeDurationsJson;

    public CumulativeVoiceDuration(String cumulativeDurationsJson) {
        this.cumulativeDurationsJson = cumulativeDurationsJson;
    }

    public String getJson() {
        return cumulativeDurationsJson;
    }
}
