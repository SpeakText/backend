package com.speaktext.backend.book.inspection.application;

import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PendingBookSearcher {

    private final PendingBookRepository pendingBookRepository;

    public PendingBook findByIdentificationNumber(String identificationNumber) {
        return pendingBookRepository.findByIdentificationNumber(identificationNumber);
    }

}
