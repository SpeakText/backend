package com.speaktext.backend.book.application;

import com.speaktext.backend.book.application.dto.ScriptMetaResponse;
import com.speaktext.backend.book.application.dto.ScriptResponse;
import com.speaktext.backend.book.domain.PendingBookChunks;
import com.speaktext.backend.book.domain.Script;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScriptTransformationService {

    private final ScriptInvoker scriptInvoker;
    private final ScriptPartitioner scriptPartitioner;
    private final ChunkDispatcher chunkDispatcher;
    private final ScriptBuilder scriptBuilder;
    private final ScriptSearcher scriptSearcher;

    public void announceScriptGeneration(Long pendingBookId) {
        scriptInvoker.announce(pendingBookId);
    }

    public void prepareScriptGeneration(Long pendingBookId) {
        PendingBookChunks chunks = scriptPartitioner.split(pendingBookId);
        scriptBuilder.build(chunks, pendingBookId);
        chunkDispatcher.dispatch(chunks);
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

}
