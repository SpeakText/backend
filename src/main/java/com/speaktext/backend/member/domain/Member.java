package com.speaktext.backend.member.domain;

import com.speaktext.backend.member.application.exception.AuthException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.speaktext.backend.member.application.exception.AuthExceptionType.PASSWORD_NOT_MATCH;

@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long memberId;

    @Column(nullable = false, unique = true, length = 64)
    private String identifier;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 200)
    private String email;

    public Member(String identifier, String password, String name, String email) {
        this.identifier = identifier;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static Member of(String identifier, String name, String email, String password, PasswordEncoder passwordEncoder) {
        return new Member(identifier, passwordEncoder.encode(password), name, email);
    }

    public void isPasswordMatch(String rawPassword, PasswordEncoder passwordEncoder) {
        try {
            passwordEncoder.matches(rawPassword, this.password);
        } catch (Exception e) {
            throw new AuthException(PASSWORD_NOT_MATCH);
        }
    }

}
