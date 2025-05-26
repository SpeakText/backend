package com.speaktext.backend.book.selling_book.presentation;

import com.speaktext.backend.auth.presentation.anotation.Member;
import com.speaktext.backend.book.selling_book.application.ReadingProgressService;
import com.speaktext.backend.book.selling_book.application.dto.ReadingProgressResponse;
import com.speaktext.backend.book.selling_book.presentation.dto.ReadingProgressRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reading-progress")
public class ReadingProgressController {

    private final ReadingProgressService readingProgressService;

    @PostMapping("/{sellingBookId}")
    public ResponseEntity<Void> updateReadingProgress(
            @Member Long memberId,
            @Valid ReadingProgressRequest request,
            @PathVariable @NotNull Long sellingBookId)
    {
        Long currentIndex = request.currentIndex();
        readingProgressService.updateReadingProgress(currentIndex, memberId, sellingBookId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{sellingBookId}")
    public ResponseEntity<ReadingProgressResponse> getReadingProgress(
            @Member Long memberId,
            @PathVariable @NotNull String sellingBookId)
    {

        ReadingProgressResponse response = readingProgressService.getReadingProgress(memberId, Long.valueOf(sellingBookId));
        return ResponseEntity.ok(response);
    }

}
