package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.ScriptFragment;
import com.speaktext.backend.book.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import com.speaktext.backend.book.exception.BookException;
import com.speaktext.backend.book.exception.BookExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.speaktext.backend.book.exception.BookExceptionType.NOT_SCRIPT_AUTHOR;
import static com.speaktext.backend.book.exception.BookExceptionType.SCRIPT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class ScriptSearcher {

    private final ScriptFragmentRepository scriptFragmentRepository;
    private final ScriptRepository scriptRepository;

    public Optional<Script> findByIdentificationNumber(String identificationNumber) {
        return scriptRepository.findByIdentificationNumber(identificationNumber);
    }

    Optional<ScriptFragment> findLastScriptFragment(String identificationNumber) {
        return scriptFragmentRepository.findLastScriptFragment(identificationNumber);
    }

    public List<ScriptFragment> findScriptFragmentsByIdentificationNumber(Long authorId, String identificationNumber) {
        Script script = scriptRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new BookException(SCRIPT_NOT_FOUND));
        if (!isScriptAuthor(script, authorId)) {
            throw new BookException(NOT_SCRIPT_AUTHOR);
        }
        return scriptFragmentRepository.findByIdentificationNumberOrderByIndex(identificationNumber);
    }

    private boolean isScriptAuthor(Script script, Long authorId) {
        return script.getAuthorId().equals(authorId);
    }

}
