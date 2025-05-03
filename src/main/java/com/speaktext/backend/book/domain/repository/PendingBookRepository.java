package com.speaktext.backend.book.domain.repository;

import com.speaktext.backend.book.domain.PendingBook;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public interface PendingBookRepository {

    void save(PendingBook pendingBook);
    List<PendingBook> findPendingBooks();
    PendingBook findByIdentificationNumber(String identificationNumber);
    void deleteByIdentificationNumber(@NotBlank String identificationNumber);
}
