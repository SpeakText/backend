package com.speaktext.backend.book.selling_book.application;

import com.speaktext.backend.author.domain.repository.AuthorRepository;
import com.speaktext.backend.author.exception.AuthorException;
import com.speaktext.backend.book.inspection.application.PendingBookSearcher;
import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.script.application.implement.ScriptSearcher;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.exception.ScriptException;
import com.speaktext.backend.book.selling_book.application.dto.PublishBookResponse;
import com.speaktext.backend.book.selling_book.application.dto.PublishedBookResponse;
import com.speaktext.backend.book.selling_book.domain.SellingBook;
import com.speaktext.backend.book.selling_book.domain.repository.SellingBookRepository;
import com.speaktext.backend.book.selling_book.exception.SellingBookException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.speaktext.backend.author.exception.AuthorExceptionType.AUTHOR_NOT_FOUND;
import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;
import static com.speaktext.backend.book.selling_book.exception.SellingBookExceptionType.NO_MERGED_VOICE;

@Service
@RequiredArgsConstructor
public class SellingBookService {

    private final ScriptSearcher scriptSearcher;
    private final PendingBookSearcher pendingBookSearcher;
    private final SellingBookRepository sellingBookRepository;
    private final AuthorRepository authorRepository;

    public PublishBookResponse publishBook(Long authorId, String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));
        if (!script.hasMergedVoice()) {
            throw new SellingBookException(NO_MERGED_VOICE);
        }
        PendingBook pendingBook = pendingBookSearcher.findByIdentificationNumber(identificationNumber);
        SellingBook sellingBook = SellingBook.from(pendingBook);
        pendingBook.markAsDone();
        return new PublishBookResponse(
            sellingBookRepository.save(sellingBook).getSellingBookId()
        );
    }

    public List<PublishedBookResponse> getPublishedBook() {
        return sellingBookRepository.findAll()
                .stream()
                .map(sellingBook -> PublishedBookResponse.fromDomain(
                        sellingBook,
                        authorRepository.findById(sellingBook.getAuthorId())
                                .orElseThrow(() -> new AuthorException(AUTHOR_NOT_FOUND))
                                .getName()
                ))
                .toList();
    }
}
