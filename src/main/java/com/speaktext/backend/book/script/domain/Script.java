package com.speaktext.backend.book.script.domain;

import com.speaktext.backend.book.script.exception.ScriptException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.speaktext.backend.book.script.exception.ScriptExceptionType.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Script {

    public enum VoiceStatus {
        NOT_GENERATED,
        FRAGMENTS_VOICE_GENERATED,
        MERGE_REQUESTED,
        MERGED_VOICE_GENERATED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scriptId;

    private Long authorId;
    private String identificationNumber;
    private String title;

    @Enumerated(EnumType.STRING)
    private NarrationVoiceType narrationVoice;
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
                .narrationVoice(NarrationVoiceType.NO_VOICE)
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
        if (this.fragmentsCount >= this.totalFragments) {
            this.isCompleted = true;
            this.fragmentsCount = 0;
        }
    }

    public void updateNarrationVoice(NarrationVoiceType characterVoiceType) {
        this.narrationVoice = characterVoiceType;
    }

    public void updateMergedVoicePathAndVoiceLengthInfo(String mergedVoicePath, String voiceLengthInfo) {
        this.mergedVoicePath = mergedVoicePath;
        this.voiceLengthInfo = voiceLengthInfo;
    }

    public boolean hasVoice() {
        return this.narrationVoice != NarrationVoiceType.NO_VOICE;
    }

    public boolean hasMergedVoice() {
        return this.mergedVoicePath != null;
    }

    public void markVoiceStatusAsFragmentsGenerated() {
        if (this.voiceStatus != VoiceStatus.NOT_GENERATED) {
            throw new ScriptException(VOICE_STATUS_NOT_NOT_GENERATED);
        }
        this.voiceStatus = VoiceStatus.FRAGMENTS_VOICE_GENERATED;
    }

    public void markVoiceStatusAsMergedVoiceGenerated() {
        if (this.voiceStatus != VoiceStatus.MERGE_REQUESTED) {
            throw new ScriptException(VOICE_STATUS_NOT_MERGE_REQUESTED);
        }
        this.voiceStatus = VoiceStatus.MERGED_VOICE_GENERATED;
    }

    public void markVoiceStatusAsMergeRequested() {
        if (this.voiceStatus != VoiceStatus.FRAGMENTS_VOICE_GENERATED) {
            throw new ScriptException(VOICE_STATUS_NOT_FRAGMENTS_VOICE_GENERATED);
        }
        this.voiceStatus = VoiceStatus.MERGE_REQUESTED;
    }
}
