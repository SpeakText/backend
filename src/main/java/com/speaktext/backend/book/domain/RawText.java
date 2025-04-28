package com.speaktext.backend.book.domain;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "raw_texts")
public class RawText {

    @Id
    private String id;
    private String identificationNumber;
    private String rawText;

    public RawText(String identificationNumber, String rawText) {
        this.identificationNumber = identificationNumber;
        this.rawText = rawText;
    }

}
