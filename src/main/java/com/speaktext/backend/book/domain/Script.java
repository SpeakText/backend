package com.speaktext.backend.book.domain;

import com.speaktext.backend.book.application.dto.CharacterDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Script {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scriptId;

    private Long authorId;
    private String identificationNumber;
    private String title;
    private int totalFragments;
    private int fragmentsCount;
    private boolean isCompleted;

    public static Script createInitial(String identificationNumber, String title, int fragmentsCount, Long authorId) {
        return Script.builder()
                .identificationNumber(identificationNumber)
                .title(title)
                .isCompleted(false)
                .totalFragments(fragmentsCount)
                .fragmentsCount(0)
                .authorId(authorId)
                .build();
    }

    public void increaseProgress() {
        this.fragmentsCount++;
        if (this.fragmentsCount == this.totalFragments) {
            this.isCompleted = true;
        }
    }

}
