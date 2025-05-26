package com.speaktext.backend.book.inspection.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class PendingBook {

    /**
     * REJECTED : 작품 검수 거부
     * PENDING : 작품 검수 대기
     * APPROVED : 작품 검수 승인
     * SCRIPTED : 스크립트 생성된 상태
     * DONE : 출판된 상태
     */
    public enum InspectionStatus {
        REJECTED, PENDING, APPROVED, SCRIPTED, DONE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pendingBookId;
    private Long authorId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String coverUrl;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false, unique = true)
    private String identificationNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "inspection_status")
    private InspectionStatus inspectionStatus;

    private boolean isScripted;

    private PendingBook(String title, String description, String coverUrl, BigDecimal price, String identificationNumber, Long authorId, boolean isScripted) {
        this.title = title;
        this.description = description;
        this.coverUrl = coverUrl;
        this.price = price;
        this.identificationNumber = identificationNumber;
        this.authorId = authorId;
        this.isScripted = isScripted;
    }

    public static PendingBook of(String title, String description, String coverUrl, BigDecimal price, String identificationNumber, Long authorId) {
        return new PendingBook(title, description, coverUrl, price, identificationNumber, authorId, false);
    }

    public void pending() {
        this.inspectionStatus = InspectionStatus.PENDING;
    }

    public void approve() {
        this.inspectionStatus = InspectionStatus.APPROVED;
    }

    public void reject() {
        this.inspectionStatus = InspectionStatus.REJECTED;
    }

    public void markAsDone() {
        this.inspectionStatus = InspectionStatus.DONE;
    }

    public void markAsScripted() {
        this.isScripted = true;
        this.inspectionStatus = InspectionStatus.SCRIPTED;
    }

    public boolean isDone() {
        return this.inspectionStatus == InspectionStatus.DONE;
    }

}
