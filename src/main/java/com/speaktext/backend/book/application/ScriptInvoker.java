package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.event.ScriptGenerationEvent;
import com.speaktext.backend.book.domain.PendingBook;
import com.speaktext.backend.book.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.exception.BookException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static com.speaktext.backend.book.exception.BookExceptionType.ALREADY_SCRIPTED_BOOK;
import static com.speaktext.backend.book.exception.BookExceptionType.NO_APPROVED_PENDING_BOOK;

@Component
@RequiredArgsConstructor
public class ScriptInvoker {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final PendingBookRepository pendingBookRepository;

    public void announce(Long pendingBookId) {
        validate(pendingBookId);
        applicationEventPublisher.publishEvent(new ScriptGenerationEvent(pendingBookId));
    }

    private void validate(Long pendingBookId) {
        PendingBook pendingBook = pendingBookRepository.find(pendingBookId);
        if (pendingBook.getInspectionStatus() != PendingBook.InspectionStatus.APPROVED) {
            throw new BookException(NO_APPROVED_PENDING_BOOK);
        }

        if (pendingBook.isScripted()) {
            throw new BookException(ALREADY_SCRIPTED_BOOK);
        }
    }

}
