package com.speaktext.backend.book.script.application.implement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScriptParser {

    private static final Pattern SCRIPT_PATTERN = Pattern.compile("\\[스크립트 시작\\](.*?)\\[스크립트 끝\\]", Pattern.DOTALL);
    private static final Pattern CHARACTER_PATTERN = Pattern.compile("\\[등장인물 업데이트 시작\\](.*?)\\[등장인물 업데이트 끝\\]", Pattern.DOTALL);

    public String extractScriptText(String content) {
        return extractSection(content, SCRIPT_PATTERN);
    }

    public String extractCharacterJson(String content) {
        return extractSection(content, CHARACTER_PATTERN);
    }

    private String extractSection(String content, Pattern pattern) {
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group(1).trim() : "";
    }

}