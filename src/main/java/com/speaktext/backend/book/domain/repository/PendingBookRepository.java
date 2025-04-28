package com.speaktext.backend.book.domain.repository;

import com.speaktext.backend.book.domain.PendingBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingBookRepository extends JpaRepository<PendingBook, Long> {
}
