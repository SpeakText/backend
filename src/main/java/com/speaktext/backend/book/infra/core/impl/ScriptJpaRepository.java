package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.Script;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScriptJpaRepository extends JpaRepository<Script, Long> {

    Optional<Script> findByIdentificationNumber(String identificationNumber);
    List<Script> findByAuthorId(Long authorId);

}
