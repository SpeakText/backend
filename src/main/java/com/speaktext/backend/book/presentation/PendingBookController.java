package com.speaktext.backend.book.presentation;

import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.book.application.PendingBookService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book/pending")
public class PendingBookController {

    private final PendingBookService pendingBookService;

    @DeleteMapping("/{identificationNumber}")
    public ResponseEntity<Void> deleteRawText(
            @Author Long adminId,
            @PathVariable @NotBlank String identificationNumber
    ) {
        pendingBookService.deleteRawTextAndPendingBook(identificationNumber);
        return ResponseEntity.ok().build();
    }

}
