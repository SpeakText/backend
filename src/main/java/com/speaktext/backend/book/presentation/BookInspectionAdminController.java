package com.speaktext.backend.book.presentation;

import com.speaktext.backend.book.application.BookInspectionService;
import com.speaktext.backend.book.application.dto.BookInspectionMetaResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/inspection")
public class BookInspectionAdminController {

    private final BookInspectionService bookInspectionService;

    public BookInspectionAdminController(BookInspectionService bookInspectionService) {
        this.bookInspectionService = bookInspectionService;
    }

    @GetMapping
    public String viewPendingBookList(Model model) {
        List<BookInspectionMetaResponse> pendingBooks = bookInspectionService.getPendingBooks();
        model.addAttribute("pendingBooks", pendingBooks);
        return "admin/pending-book-list";
    }

    @GetMapping("/rawtext/{identificationNumber}")
    @ResponseBody
    public String getRawText(
            @PathVariable @NotBlank String identificationNumber
    ) {
        return bookInspectionService.getRawText(identificationNumber);
    }

}
