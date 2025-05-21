package com.speaktext.backend.admin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminPageController {

    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin/login";
    }

    @GetMapping("/admin/inspection")
    public String showInspectionPage() {
        return "admin/pending-book-list";
    }

    @GetMapping("/admin/script")
    public String showScriptPage() {
        return "admin/script-generation";
    }

    @GetMapping("/admin/merge")
    public String showMergeRequestPage() {
        return "admin/merge-request";
    }
    
}
