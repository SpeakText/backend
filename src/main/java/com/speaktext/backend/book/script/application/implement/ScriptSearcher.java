package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.script.domain.repository.ScriptRepository;
import com.speaktext.backend.book.script.exception.BookException;
import com.speaktext.backend.book.script.exception.ScriptException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.speaktext.backend.book.script.exception.BookExceptionType.NOT_SCRIPT_AUTHOR;
import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_FRAGMENT_NOT_FOUND;
import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ScriptSearcher {

    private final ScriptFragmentRepository scriptFragmentRepository;
    private final ScriptRepository scriptRepository;

    public Optional<Script> findByIdentificationNumber(String identificationNumber) {
        return scriptRepository.findByIdentificationNumber(identificationNumber);
    }

    public Optional<ScriptFragment> findLastScriptFragment(String identificationNumber) {
        return scriptFragmentRepository.findLastScriptFragment(identificationNumber);
    }

    public Page<ScriptFragment> findScriptFragmentsByIdentificationNumber(Long authorId, String identificationNumber, Pageable pageable) {
        Script script = scriptRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));
        if (isNotScriptAuthor(script, authorId)) {
            throw new BookException(NOT_SCRIPT_AUTHOR);
        }
        return scriptFragmentRepository.findByIdentificationNumberOrderByIndexPage(identificationNumber, pageable);
    }

    private boolean isNotScriptAuthor(Script script, Long authorId) {
        return !script.getAuthorId().equals(authorId);
    }

    public Page<Script> findByAuthorIdAndIsCompleted(Long authorId, boolean isCompleted, Pageable pageable) {
        return scriptRepository.findByAuthorIdAndIsCompleted(authorId, isCompleted, pageable);
    }

    public Script findByScriptId(Long scriptId) {
        return scriptRepository.findById(scriptId)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));
    }

    public ScriptFragment findScriptFragmentsByIdentificationNumberAndIndex(String identificationNumber, Long index) {
        return scriptFragmentRepository.findByIdentificationNumberAndIndex(identificationNumber, index)
                .orElseThrow(() -> new ScriptException(SCRIPT_FRAGMENT_NOT_FOUND));
    }

    public List<ScriptFragment> findScriptFragmentsByIdentificationNumber(String identificationNumber) {
        return scriptFragmentRepository.findByIdentificationNumber(identificationNumber);
    }

    public List<Script> findMergeRequested() {
        return scriptRepository.findByMergeRequested();
    }

    public List<ScriptFragment> findScriptChunkByReadingIndex(String identificationNumber, Long readingIndex) {
        return scriptFragmentRepository.findChunkByIdentificationNumberAndIndex(identificationNumber, readingIndex);
    }

}
