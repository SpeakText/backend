package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.application.implement.ScriptSearcher;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.client.gpt.CharacterVibeGptAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CharacterVibeGenerator {

    private final ScriptSearcher scriptSearcher;
    private final CharacterVibeGptAdapter characterVibeGptAdapter;
    private final CharacterVibePromptBuilder characterVibePromptBuilder;

    public String generateVibe(String identificationNumber, Long index, String speaker) {
        ScriptFragment currentSentence = findCurrentSentence(identificationNumber, index);
        ScriptFragment prevSentence = findOptionalSentence(identificationNumber, index - 1);
        ScriptFragment nextSentence = findOptionalSentence(identificationNumber, index + 1);
        String prompt = characterVibePromptBuilder.build(prevSentence, currentSentence, nextSentence, speaker);
        return characterVibeGptAdapter.generateVibe(prompt);
    }

    private ScriptFragment findCurrentSentence(String identificationNumber, Long index) {
        return scriptSearcher.findScriptFragmentsByIdentificationNumberAndIndex(identificationNumber, index);
    }

    private ScriptFragment findOptionalSentence(String identificationNumber, Long index) {
        try {
            return scriptSearcher.findScriptFragmentsByIdentificationNumberAndIndex(identificationNumber, index);
        } catch (Exception e) {
            return null;
        }
    }

}