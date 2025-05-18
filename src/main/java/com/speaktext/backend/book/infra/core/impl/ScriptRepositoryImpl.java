package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScriptRepositoryImpl implements ScriptRepository {

    private final ScriptJpaRepository scriptJpaRepository;

    @Override
    public Optional<Script> findById(Long scriptId) {
        return scriptJpaRepository.findById(scriptId);
    }

    @Override
    public Script save(Script script) {
        return scriptJpaRepository.save(script);
    }

    @Override
    public Optional<Script> findByIdentificationNumber(String identificationNumber) {
        return scriptJpaRepository.findByIdentificationNumber(identificationNumber);
    }

    @Override
    public Page<Script> findByAuthorIdAndIsCompleted(Long authorId, boolean isCompleted, Pageable pageable) {
        return scriptJpaRepository.findByAuthorIdAndIsCompleted(authorId, isCompleted, pageable);
    }

    @Override
    public void saveMergedVoicePathAndVoiceLengthInfo(Script script, String mergedVoicePath, String voiceLengthInfo) {
        script.updateMergedVoicePathAndVoiceLengthInfo(mergedVoicePath, voiceLengthInfo);
        scriptJpaRepository.save(script);
    }

}
