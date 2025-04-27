package com.speaktext.backend.member.presentation;

import com.speaktext.backend.member.application.SignInService;
import com.speaktext.backend.member.application.dto.SignInSuccessResponse;
import com.speaktext.backend.member.presentation.dto.SignInRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;

    @PostMapping("/signin")
    public ResponseEntity<SignInSuccessResponse> signIn(
            @Valid @RequestBody SignInRequest request, HttpServletResponse response
    ) {
        SignInSuccessResponse sessionResponse = signInService.signIn(request.id(), request.password());
        addSessionCookie(response, sessionResponse.sessionId());
        return ResponseEntity.ok(sessionResponse);
    }

    private void addSessionCookie(HttpServletResponse response, String sessionId) {
        Cookie cookie = new Cookie("SESSIONID", sessionId);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
