package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.selling_book.domain.ReadingProgress;
import com.speaktext.backend.book.selling_book.domain.repository.ReadingProgressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReadingProgressRepositoryImpl implements ReadingProgressRepository {

    private final ReadingProgressJpaRepository readingProgressJpaRepository;

    @Override
    public Optional<ReadingProgress> findByMemberIdAndSellingBookId(Long memberId, Long sellingBookId) {
        return readingProgressJpaRepository.findByMember_MemberIdAndSellingBook_SellingBookId(memberId, sellingBookId);
    }

    @Override
    public void save(ReadingProgress progress) {
        readingProgressJpaRepository.save(progress);
    }

}
