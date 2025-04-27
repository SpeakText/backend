package com.speaktext.backend.book.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class PendingBook {

    private enum InspectionStatus {
        REJECTED, PENDING, APPROVED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pendingBookId;

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

    private PendingBook(String title, String description, String coverUrl, BigDecimal price, String identificationNumber) {
        this.title = title;
        this.description = description;
        this.coverUrl = coverUrl;
        this.price = price;
        this.identificationNumber = identificationNumber;
    }

    public static PendingBook of(String title, String description, String coverUrl, BigDecimal price, String identificationNumber) {
        return new PendingBook(title, description, coverUrl, price, identificationNumber);
    }

    public void pending() {
        this.inspectionStatus = InspectionStatus.PENDING;
    }

}
