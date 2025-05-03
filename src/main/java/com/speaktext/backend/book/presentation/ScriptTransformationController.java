package com.speaktext.backend.book.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import com.speaktext.backend.book.application.ScriptTransformationService;
import com.speaktext.backend.book.presentation.dto.ScriptRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
