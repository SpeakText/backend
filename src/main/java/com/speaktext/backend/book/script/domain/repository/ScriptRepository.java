package com.speaktext.backend.book.script.domain.repository;

import com.speaktext.backend.book.script.domain.Script;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ScriptRepository {

    Optional<Script> findById(Long aLong);
    Script save(Script script);
    Optional<Script> findByIdentificationNumber(String identificationNumber);
    Page<Script> findByAuthorIdAndIsCompleted(Long authorId, boolean isCompleted, Pageable pageable);

}
