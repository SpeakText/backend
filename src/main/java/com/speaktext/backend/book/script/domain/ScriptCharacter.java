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

    @Enumerated(EnumType.STRING)
    private VoiceType voiceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id")
    private Script script;
    private boolean appearedInScript;

    public ScriptCharacter(String name, String description, String characterKey, VoiceType voiceType, Script script, boolean appearedInScript) {
        this.name = name;
        this.description = description;
        this.characterKey = characterKey;
        this.voiceType = voiceType;
        this.script = script;
        this.appearedInScript = appearedInScript;
    }

    public static ScriptCharacter init(String name, String description, String characterKey, Script script, boolean appearedInScript) {
        return new ScriptCharacter(name, description, characterKey, VoiceType.NO_VOICE, script, appearedInScript);
    }

    public void updateName(String newName) {
        this.name = newName;
    }


    public void updateVoice(VoiceType newVoice) {
        this.voiceType = newVoice;
    }

    public boolean hasVoice() {
        return voiceType != VoiceType.NO_VOICE;
    }

}
