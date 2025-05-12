package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ScriptFragmentParser {

    private static final String NARRATION_SPEAKER_KEY = "나레이션 - narration";

    public List<ScriptFragment> parseFragments(String scriptText) {
        List<ScriptFragment> fragments = new ArrayList<>();

        Arrays.stream(scriptText.split("\n"))
                .map(String::trim)
                .filter(line -> line.contains(":"))
                .forEach(line -> {
                    int sepIndex = line.indexOf(":");
                    String speaker = line.substring(0, sepIndex).trim();
                    String utterance = line.substring(sepIndex + 1).trim().replaceAll("^\"|\"$", "");
                    boolean isNarration = speaker.equalsIgnoreCase(NARRATION_SPEAKER_KEY);

                    fragments.add(ScriptFragment.builder()
                            .speaker(speaker)
                            .utterance(utterance)
                            .narration(isNarration)
                            .build());
                });

        return fragments;
    }

    public Set<String> extractAppearedCharacterKeys(List<ScriptFragment> fragments) {
        return fragments.stream()
                .map(ScriptFragment::getSpeaker)
                .filter(speaker -> !speaker.equalsIgnoreCase(NARRATION_SPEAKER_KEY))
                .collect(Collectors.toSet());
    }

}
