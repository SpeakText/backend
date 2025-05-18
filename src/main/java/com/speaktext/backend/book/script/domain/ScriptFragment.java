package com.speaktext.backend.book.script.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "script_fragments")
@Builder(toBuilder = true)
@Getter
public class ScriptFragment {

    @Id
    private ObjectId scriptFragmentId;
    private String identificationNumber;
    private String speaker;
    private String utterance;
    private Long index;
    private boolean narration;
    private String voicePath;
    private Long voiceLength;

    public static ScriptFragment of(String identificationNumber, Long index, String speaker, String utterance, String voicePath, Long voiceLength) {
        boolean isNarration = speaker != null && speaker.startsWith("나레이션");

        return ScriptFragment.builder()
                .identificationNumber(identificationNumber)
                .index(index)
                .speaker(speaker)
                .utterance(utterance)
                .narration(isNarration)
                .voicePath(voicePath)
                .voiceLength(voiceLength)
                .build();
    }

    public void confirmIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public void modify(String speaker, String utterance) {
        this.speaker = speaker;
        this.utterance = utterance;
    }

}
