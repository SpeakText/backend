package com.speaktext.backend.member.presentation;

import com.speaktext.backend.member.application.MemberSignUpService;
import com.speaktext.backend.member.application.dto.SignUpSuccessResponse;
import com.speaktext.backend.member.presentation.dto.SignUpRequest;
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
public class MemberSignUpController {

    private final MemberSignUpService signUpService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpSuccessResponse> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        SignUpSuccessResponse response = signUpService.signUp(request.id(), request.name(), request.email(), request.password());
        return ResponseEntity.ok(response);
    }

}
