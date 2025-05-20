package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.selling_book.domain.SellingBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellingBookJpaRepository extends JpaRepository<SellingBook, Long> {
}
