package com.speaktext.backend.book.inspection.domain.repository;

import com.speaktext.backend.book.inspection.domain.PendingBook;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface PendingBookRepository {

    PendingBook find(Long pendingBookId);
    void save(PendingBook pendingBook);
    List<PendingBook> findPendingBooks();
    PendingBook findByIdentificationNumber(String identificationNumber);
    void deleteByIdentificationNumber(@NotBlank String identificationNumber);

}
