package com.speaktext.backend.book.selling_book.domain.repository;

import com.speaktext.backend.book.selling_book.domain.SellingBook;

import java.util.List;

public interface SellingBookRepository {

    SellingBook save(SellingBook book);

    List<SellingBook> findAll();
}
