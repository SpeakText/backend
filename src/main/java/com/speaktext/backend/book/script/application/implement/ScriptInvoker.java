package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.domain.event.ScriptGenerationEvent;
import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.script.exception.BookException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static com.speaktext.backend.book.script.exception.BookExceptionType.ALREADY_SCRIPTED_BOOK;
import static com.speaktext.backend.book.script.exception.BookExceptionType.NO_APPROVED_PENDING_BOOK;

@Component
@RequiredArgsConstructor
public class ScriptInvoker {

    private final ApplicationEventPublisher applicationEventPublisher;
    private final PendingBookRepository pendingBookRepository;

    public void announce(String identificationNumber) {
        validate(identificationNumber);
        applicationEventPublisher.publishEvent(new ScriptGenerationEvent(identificationNumber));
    }

    private void validate(String identificationNumber) {
        PendingBook pendingBook = pendingBookRepository.findByIdentificationNumber(identificationNumber);
        if (pendingBook.getInspectionStatus() != PendingBook.InspectionStatus.APPROVED) {
            throw new BookException(NO_APPROVED_PENDING_BOOK);
        }

        if (pendingBook.isScripted()) {
            throw new BookException(ALREADY_SCRIPTED_BOOK);
        }
    }

}
