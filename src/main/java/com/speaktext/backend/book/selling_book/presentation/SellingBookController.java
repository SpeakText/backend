package com.speaktext.backend.book.selling_book.presentation;

import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.auth.presentation.anotation.Member;
import com.speaktext.backend.book.selling_book.application.SellingBookService;
import com.speaktext.backend.book.selling_book.application.dto.PublishBookResponse;
import com.speaktext.backend.book.selling_book.application.dto.PublishedBookResponse;
import com.speaktext.backend.book.selling_book.presentation.dto.PublishBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/selling-book")
public class SellingBookController {

    private final SellingBookService sellingBookService;

    @PostMapping
    public ResponseEntity<PublishBookResponse> publishBook(
            @Author Long authorId,
            @RequestBody PublishBookRequest request
    ) {
        var response = sellingBookService.publishBook(authorId, request.identificationNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PublishedBookResponse>> getPublishedBook() {
        var response = sellingBookService.getPublishedBook();
        return ResponseEntity.ok(response);
    }

}
