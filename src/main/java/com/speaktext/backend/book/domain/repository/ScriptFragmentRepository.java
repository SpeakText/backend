package com.speaktext.backend.book.domain.repository;

import com.speaktext.backend.book.domain.ScriptFragment;

import java.util.List;
import java.util.Optional;

public interface ScriptFragmentRepository {

    Optional<ScriptFragment> findLastScriptFragment(String identificationNumber);
    void saveAll(List<ScriptFragment> scriptFragments);

}
