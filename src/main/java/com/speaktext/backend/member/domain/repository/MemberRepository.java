package com.speaktext.backend.member.domain.repository;

import com.speaktext.backend.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByIdentifier(String identifier);

}
