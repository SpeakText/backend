package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.selling_book.domain.ReadingProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReadingProgressJpaRepository extends JpaRepository<ReadingProgress, Long> {

    Optional<ReadingProgress> findByMember_MemberIdAndSellingBook_SellingBookId(Long memberId, Long sellingBookId);

}
