package com.speaktext.backend.book.domain.infra.impl;

import com.speaktext.backend.book.domain.PendingBook;
import com.speaktext.backend.book.domain.repository.PendingBookRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PendingBookRepositoryImpl implements PendingBookRepository {

    private final PendingBookJpaRepository pendingBookJpaRepository;

    public PendingBookRepositoryImpl(PendingBookJpaRepository pendingBookJpaRepository) {
        this.pendingBookJpaRepository = pendingBookJpaRepository;
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
