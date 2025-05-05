package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.ScriptFragment;
import com.speaktext.backend.book.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScriptFinder {

    private final ScriptFragmentRepository scriptFragmentRepository;
    private final ScriptRepository scriptRepository;

    public Optional<Script> findByIdentificationNumber(String identificationNumber) {
        return scriptRepository.findByIdentificationNumber(identificationNumber);
    }

    Optional<ScriptFragment> findLastScriptFragment(String identificationNumber) {
        return scriptFragmentRepository.findLastScriptFragment(identificationNumber);
    }

}
