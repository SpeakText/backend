package com.speaktext.backend.book.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.book.application.ScriptTransformationService;
import com.speaktext.backend.book.application.dto.ScriptMetaResponse;
import com.speaktext.backend.book.application.dto.ScriptResponse;
import com.speaktext.backend.book.presentation.dto.ScriptGetRequest;
import com.speaktext.backend.book.presentation.dto.ScriptRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/script/")
public class ScriptTransformationController {

    private final ScriptTransformationService scriptTransformationService;

    @PostMapping("/generation")
    public ResponseEntity<Void> generateScript(
            @Admin Long adminId,
            @RequestBody ScriptRequest request
    ) {
        scriptTransformationService.announceScriptGeneration(request.pendingBookId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/generated")
    public ResponseEntity<List<ScriptResponse>> getScript(
            @Author Long authorId,
            @RequestBody ScriptGetRequest request
    ) {
        var script = scriptTransformationService.getScript(authorId, request.identificationNumber());
        return ResponseEntity.ok(script);
    }

    @GetMapping("/progress")
    public ResponseEntity<List<ScriptMetaResponse>> getScriptProgress(
            @Author Long authorId
    ) {
        var authorScripts = scriptTransformationService.getAuthorScripts(authorId);
        return ResponseEntity.ok(authorScripts);
    }

}
