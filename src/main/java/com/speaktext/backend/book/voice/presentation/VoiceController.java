package com.speaktext.backend.book.voice.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.auth.presentation.anotation.Member;
import com.speaktext.backend.book.voice.application.VoiceService;
import com.speaktext.backend.book.voice.application.dto.*;
import com.speaktext.backend.book.voice.presentation.dto.VoiceDownloadRequest;
import com.speaktext.backend.book.voice.presentation.dto.VoiceGenerateRequest;
import com.speaktext.backend.book.voice.presentation.dto.VoiceMergeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/merge/approve")
    public ResponseEntity<Void> mergeVoice(
            @Admin Long adminId,
            @RequestBody VoiceMergeRequest request
    ) {
        voiceService.mergeVoice(request.identificationNumber());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/download")
    public ResponseEntity<VoicePathResponse> downloadVoice(
            @Member Long memberId,
            @RequestBody VoiceDownloadRequest request
    ) {
        VoicePathResponse response = voiceService.downloadVoice(request.identificationNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{identificationNumber}/voice-length-info")
    public ResponseEntity<VoiceLengthInfoResponse> getVoiceLengthInfo(
            @Member Long memberId,
            @PathVariable String identificationNumber
    ) {
        VoiceLengthInfoResponse response = voiceService.getVoiceLengthInfo(identificationNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{identificationNumber}/voice-status")
    public ResponseEntity<VoiceStatusResponse> getVoiceStatus(
            @Author Long authorId,
            @PathVariable String identificationNumber
    ) {
        VoiceStatusResponse response = voiceService.getVoiceStatus(identificationNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/merge/requested")
    public ResponseEntity<List<MergeRequestedResponse>> getMergeRequested(
            @Admin Long adminId
    ) {
        List<MergeRequestedResponse> mergeRequested = voiceService.getMergeRequested();
        return ResponseEntity.ok(mergeRequested);
    }

    @PostMapping("/merge")
    public ResponseEntity<Void> requestMergeVoiceGeneration(
            @Author Long authorId,
            @RequestBody VoiceMergeRequest request
    ) {
        voiceService.requestMergeVoiceGeneration(request.identificationNumber());
        return ResponseEntity.ok().build();
    }

}
