package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ScriptFragmentMongoRepository extends MongoRepository<ScriptFragment, String> {

    Optional<ScriptFragment> findFirstByIdentificationNumberOrderByIndexDesc(String identificationNumber);
    boolean existsByIdentificationNumber(String identificationNumber);
    Page<ScriptFragment> findAllByIdentificationNumberOrderByIndexAsc(String identificationNumber, Pageable pageable);
    Optional<ScriptFragment> findByIdentificationNumberAndIndex(String identificationNumber, Long index);
    List<ScriptFragment> findAllByIdentificationNumberOrderByIndex(String identificationNumber);

}
