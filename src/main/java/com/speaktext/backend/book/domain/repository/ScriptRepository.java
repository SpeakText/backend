package com.speaktext.backend.book.domain.repository;

import com.speaktext.backend.book.domain.Script;

import java.util.List;
import java.util.Optional;

public interface ScriptRepository {

    Script save(Script script);
    Optional<Script> findByIdentificationNumber(String identificationNumber);
    List<Script> findByAuthorId(Long authorId);

}
