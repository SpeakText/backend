package com.speaktext.backend.book.voice.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.auth.presentation.anotation.Member;
import com.speaktext.backend.book.voice.application.VoiceService;
import com.speaktext.backend.book.voice.presentation.dto.VoiceDownloadRequest;
import com.speaktext.backend.book.voice.presentation.dto.VoiceGenerateRequest;
import com.speaktext.backend.book.voice.presentation.dto.VoiceMergeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/merge")
    public ResponseEntity<Void> mergeVoice(
            @Admin Long adminId,
            @RequestBody VoiceMergeRequest request
    ) {
        voiceService.mergeVoice(request.identificationNumber());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/download")
    public ResponseEntity<String> downloadVoice(
            @Member Long memberId,
            @RequestBody VoiceDownloadRequest request
    ) {
        String voiceFilePath = voiceService.downloadVoice(request.identificationNumber());
        return ResponseEntity.ok(voiceFilePath);
    }

    @GetMapping("/voice-length-info/{identificationNumber}")
    public ResponseEntity<String> updateVoiceLengthInfo(
            @Member Long memberId,
            @PathVariable String identificationNumber
    ) {
        String voiceLengthInfo = voiceService.getVoiceLengthInfo(identificationNumber);
        return ResponseEntity.ok(voiceLengthInfo);
    }

}
