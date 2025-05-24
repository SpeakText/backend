package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.script.domain.PendingBookChunks;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.script.domain.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ScriptBuilder {

    private final ScriptRepository scriptRepository;
    private final PendingBookRepository pendingBookRepository;

    @Transactional
    public void build(PendingBookChunks chunks, String identificationNumber) {
        PendingBook pendingBook = pendingBookRepository.findByIdentificationNumber(identificationNumber);
        Script script = scriptRepository.findByIdentificationNumber(pendingBook.getIdentificationNumber())
                .orElse(
                        Script.createInitial(
                                pendingBook.getIdentificationNumber(),
                                pendingBook.getTitle(),
                                chunks.getNumberOfPendingBookChunks(),
                                pendingBook.getAuthorId()
                        )
                );
        scriptRepository.save(script);
    }

}
