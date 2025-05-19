package com.speaktext.backend.book.voice.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.script.domain.ScriptFragment;

import java.util.ArrayList;
import java.util.List;

public class CumulativeVoiceDuration {

    private final String cumulativeDurationsJson;

    private CumulativeVoiceDuration(String cumulativeDurationsJson) {
        this.cumulativeDurationsJson = cumulativeDurationsJson;
    }

    public static CumulativeVoiceDuration fromFragments(List<ScriptFragment> fragments, ObjectMapper objectMapper) {
        List<Integer> cumulative = new ArrayList<>();
        int sum = 0;
        for (ScriptFragment fragment : fragments) {
            sum += fragment.getVoiceLength();
            cumulative.add(sum);
        }

        try {
            String json = objectMapper.writeValueAsString(cumulative);
            return new CumulativeVoiceDuration(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("CumulativeVoiceDuration JSON 직렬화 실패", e);
        }
    }

    public String getJson() {
        return cumulativeDurationsJson;
    }
}
