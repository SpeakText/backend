package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.selling_book.domain.SellingBook;
import com.speaktext.backend.book.selling_book.domain.repository.SellingBookRepository;
import com.speaktext.backend.book.selling_book.exception.SellingBookException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.speaktext.backend.book.selling_book.exception.SellingBookExceptionType.SELLING_BOOK_NOT_FOUND;

@RequiredArgsConstructor
@Repository
public class SellingBookRepositoryImpl implements SellingBookRepository {

    private final SellingBookJpaRepository sellingBookJpaRepository;

    @Override
    public SellingBook save(SellingBook book) {
        return sellingBookJpaRepository.save(book);
    }

    @Override
    public List<SellingBook> findAll() {
        return sellingBookJpaRepository.findAll();
    }

    @Override
    public SellingBook findById(Long sellingBookId) {
        return sellingBookJpaRepository.findById(sellingBookId)
                .orElseThrow(() -> new SellingBookException(SELLING_BOOK_NOT_FOUND));
    }

}
