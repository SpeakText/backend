package com.speaktext.backend.admin.application;

import com.speaktext.backend.admin.application.dto.SignInSuccessResponse;
import com.speaktext.backend.admin.domain.Admin;
import com.speaktext.backend.admin.domain.repository.AdminRepository;
import com.speaktext.backend.auth.application.SessionService;
import com.speaktext.backend.auth.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.speaktext.backend.common.type.UserType.ADMIN;
import static com.speaktext.backend.auth.exception.AuthExceptionType.NO_SUCH_MEMBER;

@Service
@RequiredArgsConstructor
public class AdminSignInService {

    private final AdminRepository adminRepository;
    private final SessionService sessionService;

    public SignInSuccessResponse signIn(String id, String rawPassword) {
        Admin admin = adminRepository.findByIdentifier(id)
                .orElseThrow(() -> new AuthException(NO_SUCH_MEMBER));
        admin.isPasswordMatch(rawPassword);
        String sessionId = sessionService.createSession(admin.getAdminId(), ADMIN);
        return new SignInSuccessResponse(sessionId);
    }

}
