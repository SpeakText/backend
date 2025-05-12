package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.script.exception.BookException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.speaktext.backend.book.script.exception.BookExceptionType.NO_PENDING_BOOK;

@Repository
public class PendingBookRepositoryImpl implements PendingBookRepository {

    private final PendingBookJpaRepository pendingBookJpaRepository;

    public PendingBookRepositoryImpl(PendingBookJpaRepository pendingBookJpaRepository) {
        this.pendingBookJpaRepository = pendingBookJpaRepository;
    }

    @Override
    public PendingBook find(Long pendingBookId) {
        return this.pendingBookJpaRepository.findById(pendingBookId)
                .orElseThrow(() -> new BookException(NO_PENDING_BOOK));
    }

    @Override
    public void save(PendingBook pendingBook) {
        this.pendingBookJpaRepository.save(pendingBook);
    }

    @Override
    public List<PendingBook> findPendingBooks() {
        return pendingBookJpaRepository.findByInspectionStatus(PendingBook.InspectionStatus.PENDING);
    }

    @Override
    public PendingBook findByIdentificationNumber(String identificationNumber) {
        return pendingBookJpaRepository.findByIdentificationNumber(identificationNumber);
    }

    @Override
    public void deleteByIdentificationNumber(String identificationNumber) {
        pendingBookJpaRepository.deleteByIdentificationNumber(identificationNumber);
    }

}
