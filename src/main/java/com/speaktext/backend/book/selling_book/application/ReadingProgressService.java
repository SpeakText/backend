package com.speaktext.backend.book.selling_book.application;

import com.speaktext.backend.book.selling_book.application.dto.ReadingProgressResponse;
import com.speaktext.backend.book.selling_book.domain.ReadingProgress;
import com.speaktext.backend.book.selling_book.domain.SellingBook;
import com.speaktext.backend.book.selling_book.domain.repository.ReadingProgressRepository;
import com.speaktext.backend.book.selling_book.domain.repository.SellingBookRepository;
import com.speaktext.backend.book.selling_book.exception.ReadingProgressException;
import com.speaktext.backend.member.domain.Member;
import com.speaktext.backend.member.domain.repository.MemberRepository;
import com.speaktext.backend.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.speaktext.backend.book.selling_book.exception.ReadingProgressExceptionType.READING_PROGRESS_NOT_FOUND;
import static com.speaktext.backend.member.exception.MemberExceptionType.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ReadingProgressService {

    private final ReadingProgressRepository readingProgressRepository;
    private final SellingBookRepository sellingBookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void updateReadingProgress(Long currentIndex, Long memberId, Long sellingBookId) {
        ReadingProgress progress = readingProgressRepository
                .findByMemberIdAndSellingBookId(memberId, sellingBookId)
                .map(existing -> updateExistingProgress(existing, currentIndex))
                .orElseGet(() -> createNewProgress(currentIndex, memberId, sellingBookId));

        readingProgressRepository.save(progress);
    }

    private ReadingProgress updateExistingProgress(ReadingProgress existing, Long currentIndex) {
        existing.updateCurrentIndex(currentIndex);
        return existing;
    }

    private ReadingProgress createNewProgress(Long currentIndex, Long memberId, Long sellingBookId) {
        SellingBook sellingBook = sellingBookRepository.findById(sellingBookId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));

        return new ReadingProgress(
                currentIndex,
                member,
                sellingBook
        );
    }

    public ReadingProgressResponse getReadingProgress(Long memberId, Long sellingBookId) {
        ReadingProgress progress = readingProgressRepository
                .findByMemberIdAndSellingBookId(memberId, sellingBookId)
                .orElseThrow(() -> new ReadingProgressException(READING_PROGRESS_NOT_FOUND));

        return ReadingProgressResponse.from(progress.getCurrentIndex());

    }
}
