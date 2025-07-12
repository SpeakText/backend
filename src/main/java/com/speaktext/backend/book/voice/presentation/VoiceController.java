package com.speaktext.backend.book.voice.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.auth.presentation.anotation.Member;
import com.speaktext.backend.book.voice.application.VoiceService;
import com.speaktext.backend.book.voice.application.dto.*;
import com.speaktext.backend.book.voice.presentation.dto.VoiceDownloadRequest;
import com.speaktext.backend.book.voice.presentation.dto.VoiceGenerateRequest;
import com.speaktext.backend.book.voice.presentation.dto.VoiceMergeRequest;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
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

    @GetMapping("/preview/{identificationNumber}")
    public ResponseEntity<Resource> previewVoice(
            @PathVariable String identificationNumber
    ) {
        try {
            Path filePath = voiceService.getMergedVoice(identificationNumber);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("audio/mpeg"))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}

