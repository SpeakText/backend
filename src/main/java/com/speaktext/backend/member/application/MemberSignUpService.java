package com.speaktext.backend.member.application;

import static com.speaktext.backend.member.exception.MemberExceptionType.ALREADY_EXISTS_MEMBER_ID;

import com.speaktext.backend.member.application.dto.SignUpSuccessResponse;
import com.speaktext.backend.member.domain.Member;
import com.speaktext.backend.common.util.PasswordEncoder;
import com.speaktext.backend.member.domain.repository.MemberRepository;
import com.speaktext.backend.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberSignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpSuccessResponse signUp(String id, String name, String email, String password) {
        if(memberRepository.findByIdentifier(id).isPresent()) {
            throw new MemberException(ALREADY_EXISTS_MEMBER_ID);
        }

        Member member = Member.of(id, name, email, password, passwordEncoder);
        Member saved = memberRepository.save(member);
        return new SignUpSuccessResponse(saved.getMemberId());
    }

}
