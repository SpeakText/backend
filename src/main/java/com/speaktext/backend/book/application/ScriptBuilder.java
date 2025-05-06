package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBook;
import com.speaktext.backend.book.domain.PendingBookChunks;
import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ScriptBuilder {

    private final ScriptRepository scriptRepository;
    private final PendingBookRepository pendingBookRepository;

    @Transactional
    public void build(PendingBookChunks chunks, Long pendingBookId) {
        PendingBook pendingBook = pendingBookRepository.find(pendingBookId);
        Script script = scriptRepository.findByIdentificationNumber(pendingBook.getIdentificationNumber())
                .orElse(
                        Script.createInitial(
                                pendingBook.getIdentificationNumber(),
                                pendingBook.getTitle(),
                                chunks.getNumberOfPendingBookChunks()
                        )
                );
        scriptRepository.save(script);
    }

}
