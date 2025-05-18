package com.speaktext.backend.book.voice.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.speaktext.backend.book.script.domain.ScriptFragment;

import java.util.ArrayList;
import java.util.List;

public class CumulativeVoiceDuration {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Integer> cumulativeDurations;

    private CumulativeVoiceDuration(List<Integer> cumulativeDurations) {
        this.cumulativeDurations = List.copyOf(cumulativeDurations);
    }

    public static CumulativeVoiceDuration fromFragments(List<ScriptFragment> fragments) {
        List<Integer> cumulative = new ArrayList<>();
        int sum = 0;
        for (ScriptFragment fragment : fragments) {
            sum += fragment.getVoiceLength();
            cumulative.add(sum);
        }
        return new CumulativeVoiceDuration(cumulative);
    }

    public String toJson() {
        try {
            return objectMapper.writeValueAsString(cumulativeDurations);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("CumulativeVoiceDuration JSON 변환 실패", e);
        }
    }
}
