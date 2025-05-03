package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.RawText;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RawTextMongoRepository extends MongoRepository<RawText, String> {

    Optional<RawText> findByIdentificationNumber(String identificationNumber);
    void deleteByIdentificationNumber(String identificationNumber);

}
