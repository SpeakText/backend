package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScriptRepositoryImpl implements ScriptRepository {

    private final ScriptJpaRepository scriptJpaRepository;

    @Override
    public Script save(Script script) {
        return scriptJpaRepository.save(script);
    }

    @Override
    public Optional<Script> findByIdentificationNumber(String identificationNumber) {
        return scriptJpaRepository.findByIdentificationNumber(identificationNumber);
    }

}
