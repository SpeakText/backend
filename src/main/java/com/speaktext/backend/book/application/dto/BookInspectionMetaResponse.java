package com.speaktext.backend.book.application.dto;

import com.speaktext.backend.book.domain.PendingBook;
import java.math.BigDecimal;

public record BookInspectionMetaResponse(
        Long pendingBookId,
        Long authorId,
        String title,
        String description,
        String coverUrl,
        BigDecimal price,
        String identificationNumber,
        PendingBook.InspectionStatus inspectionStatus
) {
    public static BookInspectionMetaResponse from(PendingBook pendingBook) {
        return new BookInspectionMetaResponse(
                pendingBook.getPendingBookId(),
                pendingBook.getAuthorId(),
                pendingBook.getTitle(),
                pendingBook.getDescription(),
                pendingBook.getCoverUrl(),
                pendingBook.getPrice(),
                pendingBook.getIdentificationNumber(),
                pendingBook.getInspectionStatus()
        );
    }

}
