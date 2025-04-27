package com.speaktext.backend.member.application;

import com.speaktext.backend.auth.UserType;
import com.speaktext.backend.auth.application.SessionService;
import com.speaktext.backend.member.application.dto.SignInSuccessResponse;
import com.speaktext.backend.member.application.exception.AuthException;
import com.speaktext.backend.member.domain.Member;
import com.speaktext.backend.member.domain.PasswordEncoder;
import com.speaktext.backend.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;

import static com.speaktext.backend.auth.UserType.MEMBER;
import static com.speaktext.backend.member.application.exception.AuthExceptionType.NO_SUCH_MEMBER;

@Service
public class SignInService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;

    public SignInService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, SessionService sessionService) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionService = sessionService;
    }

    public SignInSuccessResponse signIn(String id, String rawPassword) {
        Member member = memberRepository.findByIdentifier(id)
                .orElseThrow(() -> new AuthException(NO_SUCH_MEMBER));
        member.isPasswordMatch(rawPassword, passwordEncoder);
        String sessionId = sessionService.createSession(member.getMemberId(), MEMBER);
        return new SignInSuccessResponse(sessionId);
    }

}
