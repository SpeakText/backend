package com.speaktext.backend.book.script.application;

import com.speaktext.backend.book.inspection.application.PendingBookSearcher;
import com.speaktext.backend.book.inspection.domain.PendingBook;
import com.speaktext.backend.book.inspection.domain.repository.PendingBookRepository;
import com.speaktext.backend.book.script.application.dto.*;
import com.speaktext.backend.book.script.application.implement.ScriptInvoker;
import com.speaktext.backend.book.script.application.implement.ScriptModifier;
import com.speaktext.backend.book.script.application.implement.ScriptSearcher;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.exception.BookException;
import com.speaktext.backend.book.script.exception.ScriptException;
import com.speaktext.backend.book.script.presentation.dto.NarrationResponse;
import com.speaktext.backend.book.script.presentation.dto.ScriptContentResponse;
import com.speaktext.backend.book.script.presentation.dto.ScriptGenerationTargetResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.speaktext.backend.book.script.exception.BookExceptionType.NO_PUBLISHED_BOOK;
import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScriptService {

    private final ScriptInvoker scriptInvoker;
    private final ScriptSearcher scriptSearcher;
    private final ScriptModifier scriptModifier;
    private final PendingBookRepository pendingBookRepository;
    private final PendingBookSearcher pendingBookSearcher;

    public void announceScriptGeneration(String identificationNumber) {
        scriptInvoker.announce(identificationNumber);
    }

    public Page<ScriptResponse> getScript(Long authorId, String identificationNumber, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        var scripts = scriptSearcher.findScriptFragmentsByIdentificationNumber(authorId, identificationNumber, pageable);
        return scripts.map(ScriptResponse::from);
    }

    public Page<ScriptMetaResponse> getAuthorScripts(Long authorId, boolean isCompleted, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Script> scripts = scriptSearcher.findByAuthorIdAndIsCompleted(authorId, isCompleted, pageable);
        return scripts.map(ScriptMetaResponse::from);
    }

    public List<ScriptModificationResponse> modifyScriptFragments(Long authorId, String identificationNumber, List<ScriptFragment> scriptFragments) {
        List<ScriptFragment> modify = scriptModifier.modify(authorId, identificationNumber, scriptFragments);
        return modify.stream()
                .map(scriptFragment -> new ScriptModificationResponse(scriptFragment.getSpeaker(), scriptFragment.getUtterance()))
                .toList();
    }

    @Transactional
    public NarrationUpdateResponse updateNarration(String identificationNumber, NarrationUpdateCommand command) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));
        script.updateNarrationVoice(command.characterVoiceType());
        return new NarrationUpdateResponse(identificationNumber, script.getNarrationVoice().toString());
    }

    public NarrationResponse getNarration(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        return new NarrationResponse(script.getNarrationVoice().toString());
    }

    public List<ScriptGenerationTargetResponse> getScriptGenerationTargets() {
        List<PendingBook> pendingBooks = pendingBookRepository.findByInspectionStatus(PendingBook.InspectionStatus.APPROVED);

        return pendingBooks.stream()
                .map(pendingBook -> new ScriptGenerationTargetResponse(
                        pendingBook.getTitle(),
                        pendingBook.getAuthorId(),
                        pendingBook.getIdentificationNumber(),
                        pendingBook.isScripted()
                ))
                .toList();
    }

    /*
     * 추후, memberId에 대해서 결제한 책인지에 대한 검증 필요.
     */
    public ScriptContentResponse getScriptContent(Long memberId, String identificationNumber, Long readingIndex) {
        PendingBook pendingBook = pendingBookSearcher.findByIdentificationNumber(identificationNumber);
        if (pendingBook.getInspectionStatus() != PendingBook.InspectionStatus.DONE) {
            throw new BookException(NO_PUBLISHED_BOOK);
        }
        List<ScriptFragment> scriptContents = scriptSearcher.findScriptChunkByReadingIndex(identificationNumber, readingIndex);
        return ScriptContentResponse.fromDomain(readingIndex, scriptContents);
    }

}
