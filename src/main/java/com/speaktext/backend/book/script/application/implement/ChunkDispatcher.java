package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.script.domain.PendingBookChunk;
import com.speaktext.backend.book.script.domain.PendingBookChunks;
import com.speaktext.backend.book.script.domain.repository.PendingBookChunkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ChunkDispatcher {

    private final PendingBookRepository pendingBookRepository;
    private final ChunkUnitProcessor processor;

    public void dispatch(PendingBookChunks chunks, String identificationNumber) {
        PendingBook pendingBook = pendingBookRepository.findByIdentificationNumber(identificationNumber);
        List<PendingBookChunk> pendingBookChunks = chunks.getPendingBookChunks();
        pendingBookChunks.forEach(processor::process);
        pendingBook.markAsScripted();
        pendingBookRepository.save(pendingBook);
    }

}
