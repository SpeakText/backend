package com.speaktext.backend.author.presentation;

import com.speaktext.backend.author.application.AuthorService;
import com.speaktext.backend.author.application.dto.SignInSuccessResponse;
import com.speaktext.backend.author.presentation.dto.SignInRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/signin")
    public ResponseEntity<SignInSuccessResponse> signIn(
            @Valid @RequestBody SignInRequest request, HttpServletResponse response
    ) {
        SignInSuccessResponse sessionResponse = authorService.signIn(request.id(), request.password());
        addSessionCookie(response, sessionResponse.sessionId());
        return ResponseEntity.ok(sessionResponse);
    }

    private void addSessionCookie(HttpServletResponse response, String sessionId) {
        Cookie cookie = new Cookie("SESSIONID", sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @GetMapping("/signout")
    public ResponseEntity<String> signOut(@CookieValue(value = "SESSIONID") String sessionId) {
        authorService.signOut(sessionId);
        return ResponseEntity.ok("로그아웃에 성공하였습니다.");
    }

}
