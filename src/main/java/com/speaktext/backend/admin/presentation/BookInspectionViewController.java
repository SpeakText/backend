package com.speaktext.backend.admin.presentation;

import com.speaktext.backend.auth.presentation.anotation.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/inspection")
public class BookInspectionViewController {

    @GetMapping
    public String viewPendingBookList(@Admin Long adminId, Model model) {
        return "admin/pending-book-list";
    }

}
