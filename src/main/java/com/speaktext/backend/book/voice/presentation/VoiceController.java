package com.speaktext.backend.book.voice.presentation;

import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.book.voice.application.VoiceService;
import com.speaktext.backend.book.voice.presentation.dto.VoiceGenerateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voice")
public class VoiceController {

    private final VoiceService voiceService;

    public VoiceController(VoiceService voiceService) {
        this.voiceService = voiceService;
    }

    @PostMapping("/generate")
    public ResponseEntity<Void> generateVoice(
            @Author Long authorId,
            @RequestBody VoiceGenerateRequest request
    ) {
        voiceService.generateVoice(request.identificationNumber());
        return ResponseEntity.ok().build();
    }

}
