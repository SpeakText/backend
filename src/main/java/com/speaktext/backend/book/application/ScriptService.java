package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.ScriptMetaResponse;
import com.speaktext.backend.book.application.dto.ScriptModificationResponse;
import com.speaktext.backend.book.application.dto.ScriptResponse;
import com.speaktext.backend.book.domain.ScriptFragment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScriptService {

    private final ScriptInvoker scriptInvoker;
    private final ScriptSearcher scriptSearcher;
    private final ScriptModifier scriptModifier;

    public void announceScriptGeneration(Long pendingBookId) {
        scriptInvoker.announce(pendingBookId);
    }

    public List<ScriptResponse> getScript(Long authorId, String identificationNumber) {
        var scripts = scriptSearcher.findScriptFragmentsByIdentificationNumber(authorId, identificationNumber);
        return scripts.stream()
                .map(ScriptResponse::from)
                .toList();
    }

    public List<ScriptMetaResponse> getAuthorScripts(Long authorId) {
        var scripts = scriptSearcher.findAllScriptOfAuthor(authorId);
        return scripts.stream()
                .map(ScriptMetaResponse::from)
                .toList();
    }


    public List<ScriptModificationResponse> modifyScriptFragments(Long authorId, String identificationNumber, List<ScriptFragment> scriptFragments) {
        List<ScriptFragment> modify = scriptModifier.modify(authorId, identificationNumber, scriptFragments);
        return modify.stream()
                .map(scriptFragment -> new ScriptModificationResponse(scriptFragment.getSpeaker(), scriptFragment.getUtterance()))
                .toList();
    }

}
