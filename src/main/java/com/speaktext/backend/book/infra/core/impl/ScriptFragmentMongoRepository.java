package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.ScriptFragment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ScriptFragmentMongoRepository extends MongoRepository<ScriptFragment, String> {

    Optional<ScriptFragment> findFirstByIdentificationNumberOrderByOrderIndexDesc(String identificationNumber);
    boolean existsByIdentificationNumber(String identificationNumber);

}
