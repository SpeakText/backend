package com.speaktext.backend.book.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "script_fragments")
@Builder
public class ScriptFragment {

    @Id
    private String scriptFragmentId;
    private String identificationNumber;

    @Getter
    private String speaker;

    @Getter
    private String utterance;

    @Getter
    private Long index;

    public void confirmIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public void setIndex(long index) {
        this.index = index;
    }

}
