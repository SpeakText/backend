package com.speaktext.backend.book.domain.infra.impl;

import com.speaktext.backend.book.domain.PendingBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PendingBookJpaRepository extends JpaRepository<PendingBook, Long> {

    List<PendingBook> findByInspectionStatus(PendingBook.InspectionStatus inspectionStatus);

}
