package com.speaktext.backend.book.voice.application;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import org.springframework.stereotype.Component;

@Component
public class CharacterVibePromptBuilder {

    public String build(ScriptFragment prev, ScriptFragment current, ScriptFragment next, String speaker) {
        StringBuilder prompt = new StringBuilder();

        prompt.append("Context:\n");

        if (prev != null) {
            prompt.append("Previous: ")
                    .append(prev.getSpeaker())
                    .append(": ")
                    .append(prev.getUtterance())
                    .append("\n");
        }

        prompt.append("Current: ")
                .append(current.getSpeaker())
                .append(": ")
                .append(current.getUtterance())
                .append("\n");

        if (next != null) {
            prompt.append("Next: ")
                    .append(next.getSpeaker())
                    .append(": ")
                    .append(next.getUtterance())
                    .append("\n");
        }

        prompt.append("\nAdd audio tags to this text: \"")
                .append(current.getUtterance())
                .append("\"");

        return prompt.toString();
    }

}
