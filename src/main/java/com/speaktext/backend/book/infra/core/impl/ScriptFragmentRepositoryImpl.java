package com.speaktext.backend.book.infra.core.impl;

import com.speaktext.backend.book.domain.ScriptFragment;
import com.speaktext.backend.book.domain.repository.ScriptFragmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ScriptFragmentRepositoryImpl implements ScriptFragmentRepository {

    private final ScriptFragmentMongoRepository scriptFragmentMongoRepository;

    public Optional<ScriptFragment> findLastScriptFragment(String identificationNumber) {
        return scriptFragmentMongoRepository.findFirstByIdentificationNumberOrderByOrderIndexDesc(identificationNumber);
    }

    @Override
    public void saveAll(List<ScriptFragment> scriptFragments) {
        scriptFragmentMongoRepository.saveAll(scriptFragments);
    }

}
