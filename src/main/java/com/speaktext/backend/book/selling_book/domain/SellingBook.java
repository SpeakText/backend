package com.speaktext.backend.book.selling_book.domain;

import com.speaktext.backend.book.inspection.domain.PendingBook;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
public class SellingBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sellingBookId;

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

    private boolean isDeleted = false;

    public static SellingBook from(PendingBook pendingBook) {
        return new SellingBook(
                pendingBook.getAuthorId(),
                pendingBook.getTitle(),
                pendingBook.getDescription(),
                pendingBook.getCoverUrl(),
                pendingBook.getPrice(),
                pendingBook.getIdentificationNumber()
        );
    }

    private SellingBook(Long authorId, String title, String description, String coverUrl, BigDecimal price, String identificationNumber) {
        this.authorId = authorId;
        this.title = title;
        this.description = description;
        this.coverUrl = coverUrl;
        this.price = price;
        this.identificationNumber = identificationNumber;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

}
