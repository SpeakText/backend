package com.speaktext.backend.book.selling_book.domain.repository;

import com.speaktext.backend.book.selling_book.domain.ReadingProgress;

import java.util.Optional;

public interface ReadingProgressRepository {

    Optional<ReadingProgress> findByMemberIdAndSellingBookId(Long memberId, Long sellingBookId);
    void save(ReadingProgress progress);

}
