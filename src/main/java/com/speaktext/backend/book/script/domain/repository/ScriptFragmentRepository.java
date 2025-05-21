package com.speaktext.backend.book.script.domain.repository;

import com.speaktext.backend.book.script.domain.ScriptFragment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ScriptFragmentRepository {

    List<ScriptFragment> updateAll(List<ScriptFragment> scriptFragments);
    void saveAll(List<ScriptFragment> scriptFragments);
    Optional<ScriptFragment> findLastScriptFragment(String identificationNumber);
    Page<ScriptFragment> findByIdentificationNumberOrderByIndexPage(String identificationNumber, Pageable pageable);
    List<ScriptFragment> findByIdentificationNumberOrderByIndex(String identificationNumber);
    Optional<ScriptFragment> findByIdentificationNumberAndIndex(String identificationNumber, Long index);

    void saveVoicePathAndLength(String identificationNumber, Long index, Long voiceLength, String string);

    List<ScriptFragment> findByIdentificationNumber(String identificationNumber);
}
