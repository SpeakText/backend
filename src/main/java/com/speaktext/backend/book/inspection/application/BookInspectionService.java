package com.speaktext.backend.book.inspection.application;

import com.speaktext.backend.book.inspection.application.dto.BookInspectionCommand;
import com.speaktext.backend.book.inspection.application.dto.BookInspectionMetaResponse;
import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.inspection.domain.repository.RawTextStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.speaktext.backend.common.util.FileReader.readTxtFile;

@Service
@RequiredArgsConstructor
public class BookInspectionService {

    private final RawTextStorage rawTextStorage;
    private final PendingBookRepository pendingBookRepository;

    public void requestInspection(BookInspectionCommand command, Long authorId) {
        String rawText = readTxtFile(command.txtFile());
        saveWithCompensation(rawText, command, authorId);
    }

    private void saveWithCompensation(String rawText, BookInspectionCommand command, Long authorId) {
        saveRawText(rawText, command.identificationNumber());
        savePendingBookOrRollback(rawText, command, authorId);
    }

    private void saveRawText(String rawText, String identificationNumber) {
        try {
            rawTextStorage.save(rawText, identificationNumber);
        } catch (Exception e) {
            throw new IllegalStateException("원문 텍스트 저장 실패", e);
        }
    }

    private void savePendingBookOrRollback(String rawText, BookInspectionCommand command, Long authorId) {
        try {
            PendingBook pendingBook = createPendingBook(command, authorId);
            pendingBookRepository.save(pendingBook);
        } catch (Exception e) {
            rollbackRawText(command.identificationNumber());
            throw new IllegalStateException("메타데이터 저장 실패, 원문 롤백 완료", e);
        }
    }

    private PendingBook createPendingBook(BookInspectionCommand command, Long authorId) {
        PendingBook pendingBook = PendingBook.of(
                command.title(),
                command.description(),
                command.coverUrl(),
                command.price(),
                command.identificationNumber(),
                authorId
        );
        pendingBook.pending();
        return pendingBook;
    }

    public List<BookInspectionMetaResponse> getPendingBooks() {
        return pendingBookRepository.findPendingBooks().stream()
                .map(BookInspectionMetaResponse::from)
                .toList();
    }

    private void rollbackRawText(String identificationNumber) {
        try {
            rawTextStorage.delete(identificationNumber);
        } catch (Exception rollbackEx) {
            System.err.println("RawText 롤백 실패! 식별자: " + identificationNumber);
        }
    }

    public String getRawText(String identificationNumber) {
        return rawTextStorage.load(identificationNumber);
    }

    @Transactional
    public void approvePendingBook(Long pendingBookId) {
        PendingBook pendingBook = pendingBookRepository.find(pendingBookId);
        pendingBook.approve();
    }

    @Transactional
    public void rejectPendingBook(Long pendingBookId) {
        PendingBook pendingBook = pendingBookRepository.find(pendingBookId);
        pendingBook.reject();
    }
}
