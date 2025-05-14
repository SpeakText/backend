package com.speaktext.backend.book.inspection.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.book.inspection.application.BookInspectionService;
import com.speaktext.backend.book.inspection.application.dto.BookInspectionMetaResponse;
import com.speaktext.backend.book.inspection.application.mapper.BookInspectionMapper;
import com.speaktext.backend.book.inspection.presentation.dto.BookInspectionRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookInspectionController {

    private final BookInspectionService bookInspectionService;
    private final BookInspectionMapper bookInspectionMapper;

    @PostMapping("/inspection")
    public ResponseEntity<Void> requestPendingBook(
            @Valid @ModelAttribute BookInspectionRequest request,
            @Author Long authorId
    ) {
        bookInspectionService.requestInspection(
            bookInspectionMapper.toCommand(request),
            authorId
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rawtext/{identificationNumber}")
    public String getRawText(
            @Admin Long adminId,
            @PathVariable @NotBlank final String identificationNumber
    ) {
        return bookInspectionService.getRawText(identificationNumber);
    }

    @GetMapping("/inspection/targets")
    public ResponseEntity<List<BookInspectionMetaResponse>> getPendingBooks(
            @Admin Long adminId
    ) {
        List<BookInspectionMetaResponse> pendingBooks = bookInspectionService.getPendingBooks();
        return ResponseEntity.ok(pendingBooks);
    }

    @PutMapping("/approve/{pendingBookId}")
    public ResponseEntity<Void> approvePendingBook(
            @Admin Long adminId,
            @PathVariable final Long pendingBookId
    ) {
        bookInspectionService.approvePendingBook(pendingBookId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reject/{pendingBookId}")
    public ResponseEntity<Void> rejectPendingBook(
            @Admin Long adminId,
            @PathVariable final Long pendingBookId
    ) {
        bookInspectionService.rejectPendingBook(pendingBookId);
        return ResponseEntity.ok().build();
    }

}
