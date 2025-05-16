package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import org.springframework.stereotype.Component;

@Component
public class CharacterVibePromptBuilder {

    public String build(ScriptFragment prev, ScriptFragment current, ScriptFragment next, String speaker) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are tasked with generating a VIBE instruction for a character's voice synthesis.\n\n");

        if (prev != null) {
            prompt.append("Previous line:\n")
                    .append(prev.getSpeaker())
                    .append(": ")
                    .append(prev.getUtterance())
                    .append("\n\n");
        }

        prompt.append("Current line (to generate VIBE for):\n")
                .append(current.getSpeaker())
                .append(": ")
                .append(current.getUtterance())
                .append("\n\n");

        if (next != null) {
            prompt.append("Next line:\n")
                    .append(next.getSpeaker())
                    .append(": ")
                    .append(next.getUtterance())
                    .append("\n\n");
        }

        prompt.append(String.format("Generate a VIBE instruction for %s's current line considering the flow of the conversation.", speaker));

        return prompt.toString();
    }

}
