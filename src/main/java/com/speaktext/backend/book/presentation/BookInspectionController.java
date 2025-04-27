package com.speaktext.backend.book.presentation;

import com.speaktext.backend.book.application.BookInspectionService;
import com.speaktext.backend.book.application.mapper.BookInspectionMapper;
import com.speaktext.backend.book.presentation.dto.BookInspectionRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
public class BookInspectionController {

    private final BookInspectionService bookInspectionService;
    private final BookInspectionMapper bookInspectionMapper;

    public BookInspectionController(BookInspectionService bookInspectionService, BookInspectionMapper bookInspectionMapper) {
        this.bookInspectionService = bookInspectionService;
        this.bookInspectionMapper = bookInspectionMapper;
    }

    @PostMapping("/inspection")
    public ResponseEntity<Void> requestPendingBook(
            @Valid @ModelAttribute BookInspectionRequest request
    ) {
        bookInspectionService.requestInspection(
            bookInspectionMapper.toCommand(request)
        );
        return ResponseEntity.ok().build();
    }

}
