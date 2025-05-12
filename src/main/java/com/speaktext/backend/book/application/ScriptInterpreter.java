package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.CharacterInfoDto;
import com.speaktext.backend.book.application.dto.ScriptGenerationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScriptInterpreter {

    private final LLMProvider llmProvider;
    private final ScriptParser parser;

    public ScriptGenerationResult generate(String chunkText, Map<String, CharacterInfoDto> mainCharacters) {
        String content = llmProvider.generateScript(chunkText, mainCharacters);
        return parser.parse(content);
    }

}
