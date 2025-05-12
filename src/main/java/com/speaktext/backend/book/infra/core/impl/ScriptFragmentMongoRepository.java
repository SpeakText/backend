package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ScriptFragmentMongoRepository extends MongoRepository<ScriptFragment, String> {

    Optional<ScriptFragment> findFirstByIdentificationNumberOrderByIndexDesc(String identificationNumber);
    boolean existsByIdentificationNumber(String identificationNumber);
    List<ScriptFragment> findAllByIdentificationNumberOrderByIndexAsc(String identificationNumber);
    Optional<ScriptFragment> findByIdentificationNumberAndIndex(String identificationNumber, Long index);

}
