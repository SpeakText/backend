package com.speaktext.backend.member.application;

import com.speaktext.backend.member.application.dto.SignInSuccessResponse;
import com.speaktext.backend.member.application.exception.AuthException;
import com.speaktext.backend.member.domain.Member;
import com.speaktext.backend.member.domain.PasswordEncoder;
import com.speaktext.backend.member.domain.SessionManager;
import com.speaktext.backend.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import static com.speaktext.backend.member.application.exception.AuthExceptionType.NO_SUCH_MEMBER;
import static com.speaktext.backend.member.application.exception.AuthExceptionType.PASSWORD_NOT_MATCH;

@Service
public class SignInService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    public SignInService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, SessionManager sessionManager) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionManager = sessionManager;
    }

    public SignInSuccessResponse signIn(String id, String rawPassword) {
        Member member = memberRepository.findByIdentifier(id)
                .orElseThrow(() -> new AuthException(NO_SUCH_MEMBER));
        member.isPasswordMatch(rawPassword, passwordEncoder);
        String sessionId = sessionManager.createSession(id);
        return new SignInSuccessResponse(sessionId);
    }

}
