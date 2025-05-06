package com.speaktext.backend.book.domain;

import com.speaktext.backend.book.application.dto.CharacterInfoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "script_main_characters", joinColumns = @JoinColumn(name = "script_id"))
    @MapKeyColumn(name = "character_key")
    @Column(name = "character_info")
    private Map<String, CharacterInfo> mainCharacters;

    public static Script createInitial(String identificationNumber, String title, int fragmentsCount, Long authorId) {
        return Script.builder()
                .identificationNumber(identificationNumber)
                .title(title)
                .isCompleted(false)
                .totalFragments(fragmentsCount)
                .fragmentsCount(0)
                .authorId(authorId)
                .mainCharacters(Collections.emptyMap())
                .build();
    }

    public void updateCharacterInfo(Map<String, CharacterInfoDto> updatedCharacters) {
        if (updatedCharacters == null) {
            this.mainCharacters = Collections.emptyMap();
        } else {
            this.mainCharacters = updatedCharacters.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> entry.getValue().toDomain()
                    ));
        }
    }

    public Map<String, CharacterInfoDto> getMainCharacters() {
        if (mainCharacters == null) return Collections.emptyMap();

        return mainCharacters.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> CharacterInfoDto.from(entry.getValue())
                ));
    }

    public void increaseProgress() {
        this.fragmentsCount++;
        if (this.fragmentsCount == this.totalFragments) {
            this.isCompleted = true;
        }
    }

}
