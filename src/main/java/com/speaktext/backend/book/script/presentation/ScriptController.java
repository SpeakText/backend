package com.speaktext.backend.book.script.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.book.script.application.ScriptService;
import com.speaktext.backend.book.script.application.dto.ScriptMetaResponse;
import com.speaktext.backend.book.script.application.dto.ScriptModificationResponse;
import com.speaktext.backend.book.script.application.dto.ScriptResponse;
import com.speaktext.backend.book.script.presentation.dto.ScriptGetRequest;
import com.speaktext.backend.book.script.presentation.dto.ScriptModificationRequest;
import com.speaktext.backend.book.script.presentation.dto.ScriptRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/progress/in-progress")
    public ResponseEntity<Page<ScriptMetaResponse>> getInProgressScripts(
            @Author Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        var authorInProgressScripts = scriptService.getAuthorScripts(authorId, false, page, size);
        return ResponseEntity.ok(authorInProgressScripts);
    }

    @GetMapping("/progress/completed")
    public ResponseEntity<Page<ScriptMetaResponse>> getCompletedScrips(
            @Author Long authorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        var authorCompletedScripts = scriptService.getAuthorScripts(authorId, true, page, size);
        return ResponseEntity.ok(authorCompletedScripts);
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
