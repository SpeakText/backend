package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.RawText;
import com.speaktext.backend.book.domain.repository.RawTextStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RawTextMongoStorage implements RawTextStorage {

    private final RawTextMongoRepository rawTextMongoRepository;

    @Override
    public void save(String rawText, String identificationNumber) {
        rawTextMongoRepository.save(new RawText(identificationNumber, rawText));
    }

    @Override
    public String load(String identificationNumber) {
        RawText rawText = rawTextMongoRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new IllegalArgumentException("해당 식별번호의 원문이 존재하지 않습니다."));
        return rawText.getRawText();
    }

    @Override
    public void delete(String identificationNumber) {
        rawTextMongoRepository.deleteByIdentificationNumber(identificationNumber);
    }

}
