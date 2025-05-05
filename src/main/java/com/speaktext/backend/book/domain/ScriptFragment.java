package com.speaktext.backend.book.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "script_fragments")
@Builder
public class ScriptFragment {

    @Id
    private String scriptFragmentId;
    private String identificationNumber;
    private String speaker;
    private String utterance;
    private int orderIndex;

}
