package com.speaktext.backend.book.script.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ScriptCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    private String name;

    @Column(length = 2000)
    private String description;

    private String characterKey;

    @Column(length = 100)
    private String voiceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id")
    private Script script;
    private boolean appearedInScript;

    public ScriptCharacter(String name, String description, String characterKey, String voiceId, Script script, boolean appearedInScript) {
        this.name = name;
        this.description = description;
        this.characterKey = characterKey;
        this.voiceId = voiceId;
        this.script = script;
        this.appearedInScript = appearedInScript;
    }

    public static ScriptCharacter init(String name, String description, String characterKey, Script script, boolean appearedInScript) {
        return new ScriptCharacter(name, description, characterKey, "NO_VOICE", script, appearedInScript);
    }

    public void updateName(String newName) {
        this.name = newName;
    }


    public void updateVoice(String newVoiceId) {
        this.voiceId = newVoiceId;
    }

    public boolean hasVoiceOrNotAppeared() {
        if (!appearedInScript) {
            return true;
        }
        return voiceId != null && !voiceId.equals("NO_VOICE");
    }

    public CharacterVoiceType getCharacterVoiceType() {
        return CharacterVoiceType.from(voiceId);
    }

}
