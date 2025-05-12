package com.speaktext.backend.book.inspection.domain;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "raw_texts")
public class RawText {

    @Id
    private String id;
    private String identificationNumber;

    @Getter
    private String rawText;

    public RawText(String identificationNumber, String rawText) {
        this.identificationNumber = identificationNumber;
        this.rawText = rawText;
    }

}
