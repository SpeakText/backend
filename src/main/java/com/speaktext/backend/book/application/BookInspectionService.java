package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.BookInspectionCommand;
import com.speaktext.backend.book.application.dto.BookInspectionMetaResponse;
import com.speaktext.backend.book.domain.PendingBook;
import com.speaktext.backend.book.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.domain.repository.RawTextStorage;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.speaktext.backend.common.util.FileReader.readTxtFile;

@Service
public class BookInspectionService {

    private final RawTextStorage rawTextStorage;
    private final PendingBookRepository pendingBookRepository;

    public BookInspectionService(RawTextStorage rawTextStorage, PendingBookRepository pendingBookRepository) {
        this.rawTextStorage = rawTextStorage;
        this.pendingBookRepository = pendingBookRepository;
    }

    public void requestInspection(BookInspectionCommand command, Long authorId) {
        String rawText = readTxtFile(command.txtFile());
        rawTextStorage.save(rawText, command.identificationNumber());
        PendingBook pendingBook = createPendingBook(command, authorId);
        pendingBookRepository.save(pendingBook);
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

    public String getRawText(String identificationNumber) {
        return rawTextStorage.load(identificationNumber);
    }

}
