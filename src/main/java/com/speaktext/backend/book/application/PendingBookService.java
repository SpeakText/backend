package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBook;
import com.speaktext.backend.book.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.domain.repository.RawTextStorage;
import com.speaktext.backend.book.exception.PendingBookException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.speaktext.backend.book.exception.PendingBookExceptionType.*;

@Service
@RequiredArgsConstructor
public class PendingBookService {
    private final RawTextStorage rawTextStorage;
    private final PendingBookRepository pendingBookRepository;

    @Transactional
    public void deleteRawTextAndPendingBook(@NotBlank String identificationNumber) {
        validatePendingBook(identificationNumber);

        String rawTextBackup = rawTextStorage.load(identificationNumber);

        rawTextStorage.delete(identificationNumber);

        deletePendingBookOrRollback(identificationNumber, rawTextBackup);
    }

    private void validatePendingBook(String identificationNumber) {
        PendingBook book = pendingBookRepository.findByIdentificationNumber(identificationNumber);
        if (book == null) throw new PendingBookException(PENDING_BOOK_NOT_FOUND);

        if (book.getInspectionStatus() != PendingBook.InspectionStatus.PENDING) {
            throw new PendingBookException(PENDING_BOOK_NOT_IN_PENDING_STATUS);
        }
    }

    private void deletePendingBookOrRollback(String identificationNumber, String rawTextBackup) {
        try {
            pendingBookRepository.deleteByIdentificationNumber(identificationNumber);
        } catch (Exception deletionException) {
            attemptRollback(identificationNumber, rawTextBackup, deletionException);
        }
    }

    private void attemptRollback(String identificationNumber, String rawTextBackup, Exception cause) {
        try {
            rawTextStorage.save(rawTextBackup, identificationNumber);
        } catch (Exception rollbackException) {
            throw new IllegalStateException("원문 삭제 후 원문 복구도 실패", rollbackException);
        }
        throw new IllegalStateException("PendingBook 삭제 실패, 원문은 롤백 완료됨", cause);
    }
}
