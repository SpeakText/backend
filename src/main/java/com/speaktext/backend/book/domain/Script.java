package com.speaktext.backend.book.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scriptId;

    private String identificationNumber;
    private String title;
    private int fragmentsCount;
    private boolean isCompleted;

    @ElementCollection
    @CollectionTable(name = "script_main_characters", joinColumns = @JoinColumn(name = "script_id"))
    @MapKeyColumn(name = "character_name")
    @Column(name = "description")
    @Getter
    private Map<String, String> mainCharacters;

    public static Script createInitial(String identificationNumber) {
        return Script.builder()
                .identificationNumber(identificationNumber)
                .title("Untitled")
                .isCompleted(false)
                .fragmentsCount(0)
                .mainCharacters(Collections.emptyMap())
                .build();
    }

}
