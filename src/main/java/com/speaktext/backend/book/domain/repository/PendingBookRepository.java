package com.speaktext.backend.book.domain.repository;

import com.speaktext.backend.book.domain.PendingBook;
import java.util.List;

public interface PendingBookRepository {

    void save(PendingBook pendingBook);
    List<PendingBook> findPendingBooks();

}
