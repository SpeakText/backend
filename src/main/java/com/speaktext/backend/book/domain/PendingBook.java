package com.speaktext.backend.book.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class PendingBook {

    public enum InspectionStatus {
        REJECTED, PENDING, APPROVED
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

    protected PendingBook() {
    }

    private PendingBook(String title, String description, String coverUrl, BigDecimal price, String identificationNumber, Long authorId) {
        this.title = title;
        this.description = description;
        this.coverUrl = coverUrl;
        this.price = price;
        this.identificationNumber = identificationNumber;
        this.authorId = authorId;
    }

    public static PendingBook of(String title, String description, String coverUrl, BigDecimal price, String identificationNumber, Long authorId) {
        return new PendingBook(title, description, coverUrl, price, identificationNumber, authorId);
    }

    public void pending() {
        this.inspectionStatus = InspectionStatus.PENDING;
    }

    public void approve() {
        this.inspectionStatus = InspectionStatus.APPROVED;
    }

}
