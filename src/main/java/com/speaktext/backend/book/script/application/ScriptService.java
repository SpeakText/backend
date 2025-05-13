package com.speaktext.backend.book.script.application;

import com.speaktext.backend.book.script.application.dto.*;
import com.speaktext.backend.book.script.application.implement.ScriptInvoker;
import com.speaktext.backend.book.script.application.implement.ScriptModifier;
import com.speaktext.backend.book.script.application.implement.ScriptSearcher;
import com.speaktext.backend.book.script.domain.Script;
import com.speaktext.backend.book.script.domain.ScriptFragment;
import com.speaktext.backend.book.script.exception.ScriptException;
import com.speaktext.backend.book.script.presentation.dto.NarrationResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.speaktext.backend.book.script.exception.ScriptExceptionType.SCRIPT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptInvoker scriptInvoker;
    private final ScriptSearcher scriptSearcher;
    private final ScriptModifier scriptModifier;

    public void announceScriptGeneration(Long pendingBookId) {
        scriptInvoker.announce(pendingBookId);
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
        script.updateNarrationVoice(command.voiceType());
        return new NarrationUpdateResponse(identificationNumber, script.getNarrationVoice().toString());
    }

    public NarrationResponse getNarration(String identificationNumber) {
        Script script = scriptSearcher.findByIdentificationNumber(identificationNumber)
                .orElseThrow(() -> new ScriptException(SCRIPT_NOT_FOUND));

        return new NarrationResponse(script.getNarrationVoice().toString());
    }

}
