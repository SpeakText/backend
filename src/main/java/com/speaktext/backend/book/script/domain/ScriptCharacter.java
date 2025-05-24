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
    private CharacterVoiceType characterVoiceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "script_id")
    private Script script;
    private boolean appearedInScript;

    public ScriptCharacter(String name, String description, String characterKey, CharacterVoiceType characterVoiceType, Script script, boolean appearedInScript) {
        this.name = name;
        this.description = description;
        this.characterKey = characterKey;
        this.characterVoiceType = characterVoiceType;
        this.script = script;
        this.appearedInScript = appearedInScript;
    }

    public static ScriptCharacter init(String name, String description, String characterKey, Script script, boolean appearedInScript) {
        return new ScriptCharacter(name, description, characterKey, CharacterVoiceType.NO_VOICE, script, appearedInScript);
    }

    public void updateName(String newName) {
        this.name = newName;
    }


    public void updateVoice(CharacterVoiceType newVoice) {
        this.characterVoiceType = newVoice;
    }

    public boolean hasVoiceOrNotAppeared() {
        if (!appearedInScript) {
            return true;
        }
        return characterVoiceType != CharacterVoiceType.NO_VOICE;
    }

}
