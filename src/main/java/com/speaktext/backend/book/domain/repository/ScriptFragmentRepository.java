package com.speaktext.backend.book.domain.repository;

import com.speaktext.backend.book.domain.ScriptFragment;

import java.util.List;
import java.util.Optional;

public interface ScriptFragmentRepository {

    List<ScriptFragment> updateAll(List<ScriptFragment> scriptFragments);
    void saveAll(List<ScriptFragment> scriptFragments);
    Optional<ScriptFragment> findLastScriptFragment(String identificationNumber);
    List<ScriptFragment> findByIdentificationNumberOrderByIndex(String identificationNumber);
    Optional<ScriptFragment> findByIdentificationNumberAndIndex(String identificationNumber, Long index);

}
