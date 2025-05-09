package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.PendingBook;
import com.speaktext.backend.book.domain.PendingBookChunk;
import com.speaktext.backend.book.domain.PendingBookChunks;
import com.speaktext.backend.book.domain.repository.PendingBookChunkRepository;
import com.speaktext.backend.book.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.domain.repository.RawTextStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    public PendingBookChunks split(Long pendingBookId) {
        PendingBook pendingBook = pendingBookRepository.find(pendingBookId);
        String id = pendingBook.getIdentificationNumber();
        List<PendingBookChunk> existing = pendingBookChunkRepository.findByIdentificationNumberOrderByIndex(id);
        if (!existing.isEmpty()) {
            return PendingBookChunks.of(existing.stream()
                    .map(PendingBookChunk::getChunk)
                    .toList(), id);
        }

        return splitAndReturnChunks(id);
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
