package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ScriptFragmentParser {

    private static final String NARRATION_SPEAKER_KEY = "나레이션 - narration";

    // 기본 패턴: script-character-숫자
    private static final Pattern STANDARD_SPEAKER_PATTERN = Pattern.compile("^script-character-\\d+$");

    // 대체 패턴들: 더 유연한 화자 식별
    private static final Pattern FLEXIBLE_SPEAKER_PATTERN = Pattern.compile("^script-character-.*$");
    private static final Pattern CHARACTER_NAME_PATTERN = Pattern.compile("^[가-힣a-zA-Z0-9\\s]+ - script-character-\\d+$");

    public List<ScriptFragment> parseFragments(String scriptText) {
        List<ScriptFragment> fragments = new ArrayList<>();

        Arrays.stream(scriptText.split("\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .forEach(line -> {
                    ScriptFragment fragment = parseLine(line);
                    if (fragment != null) {
                        fragments.add(fragment);
                    }
                });

        return fragments;
    }

    private ScriptFragment parseLine(String line) {
        if (!line.contains(":")) {
            log.warn("콜론이 없는 라인 무시: {}", line);
            return null;
        }

        int sepIndex = line.indexOf(":");
        String speaker = line.substring(0, sepIndex).trim();
        String utterance = line.substring(sepIndex + 1).trim().replaceAll("^\"|\"$", "");
        boolean isNarration = speaker.equalsIgnoreCase(NARRATION_SPEAKER_KEY);

        // 나레이션이면 바로 허용
        if (isNarration) {
            return ScriptFragment.builder()
                    .speaker(speaker)
                    .utterance(utterance)
                    .narration(true)
                    .build();
        }

        // 화자 유효성 검사 및 정규화
        String validatedSpeaker = validateAndNormalizeSpeaker(speaker);
        if (validatedSpeaker == null) {
            log.warn("유효하지 않은 화자 패턴, 무시: {}", line);
            return null;
        }

        return ScriptFragment.builder()
                .speaker(validatedSpeaker)
                .utterance(utterance)
                .narration(false)
                .build();
    }

    private String validateAndNormalizeSpeaker(String speaker) {
        // 1. 표준 패턴 (script-character-숫자)
        if (STANDARD_SPEAKER_PATTERN.matcher(speaker).matches()) {
            return speaker;
        }

        // 2. 캐릭터 이름 포함 패턴 (이름 - script-character-숫자)
        if (CHARACTER_NAME_PATTERN.matcher(speaker).matches()) {
            // "이름 - script-character-1" -> "script-character-1" 추출
            String[] parts = speaker.split("\\s*-\\s*");
            if (parts.length >= 2) {
                String characterId = parts[parts.length - 1].trim();
                if (STANDARD_SPEAKER_PATTERN.matcher(characterId).matches()) {
                    log.info("화자 정규화: {} -> {}", speaker, characterId);
                    return characterId;
                }
            }
        }

        // 3. 유연한 패턴 (script-character-로 시작하는 모든 것)
        if (FLEXIBLE_SPEAKER_PATTERN.matcher(speaker).matches()) {
            log.warn("비표준 화자 패턴 허용: {}", speaker);
            return speaker;
        }

        // 4. 모든 패턴 실패 시 null 반환
        return null;
    }

    public Set<String> extractAppearedCharacterKeys(List<ScriptFragment> fragments) {
        return fragments.stream()
                .map(ScriptFragment::getSpeaker)
                .filter(speaker -> !speaker.equalsIgnoreCase(NARRATION_SPEAKER_KEY))
                .collect(Collectors.toSet());
    }

}
