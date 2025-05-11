package com.speaktext.backend.book.application;

import com.speaktext.backend.book.domain.Script;
import com.speaktext.backend.book.domain.ScriptFragment;
import com.speaktext.backend.book.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.domain.repository.ScriptRepository;
import com.speaktext.backend.book.exception.BookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.speaktext.backend.book.exception.BookExceptionType.NOT_SCRIPT_AUTHOR;
import static com.speaktext.backend.book.exception.BookExceptionType.SCRIPT_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScriptModifier {

    private final ScriptFragmentRepository scriptFragmentRepository;
    private final ScriptRepository scriptRepository;

    public List<ScriptFragment> modify(Long authorId, String identificationNumber, List<ScriptFragment> scriptFragments) {
        Script script = scriptRepository.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new BookException(SCRIPT_NOT_FOUND));
        if (isNotScriptAuthor(script, authorId)) {
            throw new BookException(NOT_SCRIPT_AUTHOR);
        }
        return scriptFragmentRepository.updateAll(scriptFragments);
    }

    private boolean isNotScriptAuthor(Script script, Long authorId) {
        return !script.getAuthorId().equals(authorId);
    }

}
