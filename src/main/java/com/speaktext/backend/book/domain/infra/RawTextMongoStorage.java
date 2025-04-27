package com.speaktext.backend.book.domain.infra;

import com.speaktext.backend.book.domain.RawText;
import com.speaktext.backend.book.domain.infra.impl.RawTextMongoRepository;
import com.speaktext.backend.book.domain.repository.RawTextStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RawTextMongoStorage implements RawTextStorage {

    private final RawTextMongoRepository rawTextMongoRepository;

    @Override
    public void save(String identificationNumber, String rawText) {
        rawTextMongoRepository.save(new RawText(identificationNumber, rawText));
    }

}
