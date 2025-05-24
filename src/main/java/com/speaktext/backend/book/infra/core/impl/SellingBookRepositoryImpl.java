package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.selling_book.domain.SellingBook;
import com.speaktext.backend.book.selling_book.domain.repository.SellingBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
