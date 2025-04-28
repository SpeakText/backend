package com.speaktext.backend.admin.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminSignInViewController {

    @GetMapping("/admin/login")
    public String showLoginPage() {
        return "admin/login";
    }

}
