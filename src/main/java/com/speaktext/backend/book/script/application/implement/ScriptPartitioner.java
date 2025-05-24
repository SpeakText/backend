package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.script.domain.PendingBookChunk;
import com.speaktext.backend.book.script.domain.PendingBookChunks;
import com.speaktext.backend.book.script.domain.repository.PendingBookChunkRepository;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.inspection.domain.repository.RawTextStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScriptPartitioner {

    private static final int SPLIT_SIZE = 500;

    private final PendingBookRepository pendingBookRepository;
    private final PendingBookChunkRepository pendingBookChunkRepository;
    private final RawTextStorage rawTextStorage;

    @Transactional
    public PendingBookChunks split(String identificationNumber) {
        PendingBook pendingBook = pendingBookRepository.findByIdentificationNumber(identificationNumber);
        String id = pendingBook.getIdentificationNumber();
        List<PendingBookChunk> existing = pendingBookChunkRepository.findByIdentificationNumberOrderByIndex(id);
        if (!pendingBook.isScripted() & !existing.isEmpty()) {
            return PendingBookChunks.of(existing.stream()
                    .map(PendingBookChunk::getChunk)
                    .toList(), id);
        }

        PendingBookChunks pendingBookChunks = splitAndReturnChunks(id);
        pendingBookChunkRepository.saveAll(pendingBookChunks.getPendingBookChunks());
        return pendingBookChunks;
    }

    private PendingBookChunks splitAndReturnChunks(String identificationNumber) {
        String rawText = rawTextStorage.load(identificationNumber);
        List<String> chunks = splitIntoChunks(rawText, SPLIT_SIZE);
        return PendingBookChunks.of(chunks, identificationNumber);
    }

    private List<String> splitIntoChunks(String rawText, int splitSize) {
        List<String> sentences = splitIntoSentences(rawText);
        List<String> chunks = new ArrayList<>();

        StringBuilder currentChunk = new StringBuilder();
        for (String sentence : sentences) {
            if (currentChunk.length() + sentence.length() > splitSize && !currentChunk.isEmpty()) {
                chunks.add(currentChunk.toString().trim());
                currentChunk.setLength(0);
            }
            currentChunk.append(sentence).append(" ");
        }

        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk.toString().trim());
        }

        return chunks;
    }

    private List<String> splitIntoSentences(String text) {
        return Arrays.stream(text.split("(?<=[.!?])\\s+"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

}
