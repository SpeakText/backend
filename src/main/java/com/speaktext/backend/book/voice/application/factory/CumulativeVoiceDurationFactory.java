package com.speaktext.backend.book.voice.application.factory;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.voice.domain.CumulativeVoiceDuration;
import com.speaktext.backend.common.util.mapper.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CumulativeVoiceDurationFactory {

    private final JsonMapper jsonMapper;

    public CumulativeVoiceDuration fromFragments(List<ScriptFragment> fragments) {
        List<Integer> cumulative = calculateCumulative(fragments);
        String json = jsonMapper.toJson(cumulative);
        return new CumulativeVoiceDuration(json);
    }

    private List<Integer> calculateCumulative(List<ScriptFragment> fragments) {
        List<Integer> cumulative = new ArrayList<>();
        int sum = 0;
        for (ScriptFragment fragment : fragments) {
            cumulative.add(sum);
            sum += (int) (fragment.getVoiceLength() + 700);
        }
        return cumulative;
    }
}
