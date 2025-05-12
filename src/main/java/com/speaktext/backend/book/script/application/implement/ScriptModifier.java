package com.speaktext.backend.book.script.application.implement;

import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.domain.repository.ScriptFragmentRepository;
import com.speaktext.backend.book.script.domain.repository.ScriptRepository;
import com.speaktext.backend.book.script.exception.BookException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.speaktext.backend.book.script.exception.BookExceptionType.NOT_SCRIPT_AUTHOR;
import static com.speaktext.backend.book.script.exception.BookExceptionType.SCRIPT_NOT_FOUND;

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
