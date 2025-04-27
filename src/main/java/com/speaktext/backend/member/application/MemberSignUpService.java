package com.speaktext.backend.member.application;

import com.speaktext.backend.member.application.dto.SignUpSuccessResponse;
import com.speaktext.backend.member.domain.Member;
import com.speaktext.backend.common.util.PasswordEncoder;
import com.speaktext.backend.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberSignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberSignUpService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignUpSuccessResponse signUp(String id, String name, String email, String password) {
        Member member = Member.of(id, name, email, password, passwordEncoder);
        Member saved = memberRepository.save(member);
        return new SignUpSuccessResponse(saved.getMemberId());
    }

}
