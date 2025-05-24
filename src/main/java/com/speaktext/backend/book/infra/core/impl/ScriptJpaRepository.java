package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.script.domain.Script;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScriptJpaRepository extends JpaRepository<Script, Long> {

    Optional<Script> findByIdentificationNumber(String identificationNumber);
    Page<Script> findByAuthorIdAndIsCompleted(Long authorId, boolean isCompleted, Pageable pageable);
    List<Script> findByVoiceStatus(Script.VoiceStatus voiceStatus);

}
