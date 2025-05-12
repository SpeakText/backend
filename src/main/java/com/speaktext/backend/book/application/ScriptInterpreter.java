package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.CharacterDto;
import com.speaktext.backend.book.application.dto.ScriptGenerationResult;
import com.speaktext.backend.book.domain.ScriptFragment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScriptInterpreter {

    private final LLMProvider llmProvider;
    private final ScriptParser parser;
    private final ScriptFragmentParser fragmentParser;
    private final CharacterUpdater characterUpdater;

    public ScriptGenerationResult generate(String chunkText, List<CharacterDto> existingCharacters) {
        String content = llmProvider.generateScript(chunkText, existingCharacters);
        String scriptText = parser.extractScriptText(content);
        String characterJson = parser.extractCharacterJson(content);
        List<ScriptFragment> fragments = fragmentParser.parseFragments(scriptText);
        Set<String> appearedCharacterKeys = fragmentParser.extractAppearedCharacterKeys(fragments);
        List<CharacterDto> finalCharacterMap = characterUpdater.updateCharacterStates(characterJson, appearedCharacterKeys, existingCharacters);
        return new ScriptGenerationResult(fragments, finalCharacterMap);
    }

}
