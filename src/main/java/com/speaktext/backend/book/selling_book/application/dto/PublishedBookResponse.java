package com.speaktext.backend.book.selling_book.application.dto;

import com.speaktext.backend.book.selling_book.domain.SellingBook;

public record PublishedBookResponse(
        String identificationNumber,
        Long sellingBookId,
        String title,
        String coverUrl,
        String authorName
) {
    public static PublishedBookResponse fromDomain(SellingBook sellingBook, String authorName) {
        return new PublishedBookResponse(
                sellingBook.getIdentificationNumber(),
                sellingBook.getSellingBookId(),
                sellingBook.getTitle(),
                sellingBook.getCoverUrl(),
                authorName
        );
    }
}
