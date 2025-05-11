package com.speaktext.backend.book.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.book.application.ScriptService;
import com.speaktext.backend.book.application.dto.ScriptMetaResponse;
import com.speaktext.backend.book.application.dto.ScriptModificationResponse;
import com.speaktext.backend.book.application.dto.ScriptResponse;
import com.speaktext.backend.book.presentation.dto.ScriptGetRequest;
import com.speaktext.backend.book.presentation.dto.ScriptModificationRequest;
import com.speaktext.backend.book.presentation.dto.ScriptRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/script/")
public class ScriptController {

    private final ScriptService scriptService;

    @PostMapping("/generation")
    public ResponseEntity<Void> generateScript(
            @Admin Long adminId,
            @RequestBody ScriptRequest request
    ) {
        scriptService.announceScriptGeneration(request.pendingBookId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generated")
    public ResponseEntity<List<ScriptResponse>> getScript(
            @Author Long authorId,
            @RequestBody ScriptGetRequest request
    ) {
        var script = scriptService.getScript(authorId, request.identificationNumber());
        return ResponseEntity.ok(script);
    }

    @GetMapping("/progress")
    public ResponseEntity<List<ScriptMetaResponse>> getScriptProgress(
            @Author Long authorId
    ) {
        var authorScripts = scriptService.getAuthorScripts(authorId);
        return ResponseEntity.ok(authorScripts);
    }

    @PutMapping("/script-fragment")
    public ResponseEntity<List<ScriptModificationResponse>> modifyScriptFragment(
            @Author Long authorId,
            @RequestBody ScriptModificationRequest request
    ) {
        var response = scriptService.modifyScriptFragments(
                authorId,
                request.identificationNumber(),
                request.toScriptFragments()
        );
        return ResponseEntity.ok(response);
    }

}
