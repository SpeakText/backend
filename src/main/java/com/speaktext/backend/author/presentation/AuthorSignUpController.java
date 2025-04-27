package com.speaktext.backend.author.presentation;

import com.speaktext.backend.author.application.AuthorSignUpService;
import com.speaktext.backend.author.application.dto.SignUpSuccessResponse;
import com.speaktext.backend.author.presentation.dto.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorSignUpController {

    private final AuthorSignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpSuccessResponse> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        SignUpSuccessResponse response = signUpService.signUp(request.id(), request.name(), request.email(), request.password());
        return ResponseEntity.ok(response);
    }

}
