package com.speaktext.backend.book.script.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Script {

    public enum VoiceStatus {
        NOT_GENERATED,
        FRAGMENTS_VOICE_GENERATED,
        MERGED_VOICE_GENERATED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scriptId;

    private Long authorId;
    private String identificationNumber;
    private String title;

    @Enumerated(EnumType.STRING)
    private VoiceType narrationVoice;
    private int totalFragments;
    private int fragmentsCount;
    private boolean isCompleted;
    private String mergedVoicePath;

    @Lob
    private String voiceLengthInfo;

    @Enumerated(EnumType.STRING)
    private VoiceStatus voiceStatus;

    public static Script createInitial(String identificationNumber, String title, int fragmentsCount, Long authorId) {
        return Script.builder()
                .identificationNumber(identificationNumber)
                .title(title)
                .isCompleted(false)
                .narrationVoice(VoiceType.NO_VOICE)
                .totalFragments(fragmentsCount)
                .fragmentsCount(0)
                .authorId(authorId)
                .mergedVoicePath("")
                .voiceLengthInfo("")
                .voiceStatus(VoiceStatus.NOT_GENERATED)
                .build();
    }

    public void increaseProgress() {
        this.fragmentsCount++;
        if (this.fragmentsCount == this.totalFragments) {
            this.isCompleted = true;
        }
    }

    public void updateNarrationVoice(VoiceType voiceType) {
        this.narrationVoice = voiceType;
    }

    public void updateMergedVoicePathAndVoiceLengthInfo(String mergedVoicePath, String voiceLengthInfo) {
        this.mergedVoicePath = mergedVoicePath;
        this.voiceLengthInfo = voiceLengthInfo;
    }

    public boolean hasVoice() {
        return this.narrationVoice != VoiceType.NO_VOICE;
    }

    public boolean hasMergedVoice() {
        return this.mergedVoicePath != null;
    }

    public boolean isMergedVoiceGenerated() {
        return this.voiceStatus == VoiceStatus.MERGED_VOICE_GENERATED;
    }

    public void markVoiceStatusAsFragmentsGenerated() {
        this.voiceStatus = VoiceStatus.FRAGMENTS_VOICE_GENERATED;
    }

    public void markVoiceStatusAsMergedVoiceGenerated() {
        this.voiceStatus = VoiceStatus.MERGED_VOICE_GENERATED;
    }
}
