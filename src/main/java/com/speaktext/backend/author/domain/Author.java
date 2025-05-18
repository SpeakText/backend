package com.speaktext.backend.author.domain;

import com.speaktext.backend.common.util.PasswordEncoder;
import com.speaktext.backend.auth.exception.AuthException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.speaktext.backend.auth.exception.AuthExceptionType.PASSWORD_NOT_MATCH;

@Entity
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long authorId;

    @Column(nullable = false, unique = true, length = 64)
    private String identifier;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false, length = 200)
    private String email;

    public Author(String identifier, String password, String name, String email) {
        this.identifier = identifier;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public static Author of(String identifier, String name, String email, String password, PasswordEncoder passwordEncoder) {
        return new Author(identifier, passwordEncoder.encode(password), name, email);
    }

    public void isPasswordMatch(String rawPassword, PasswordEncoder passwordEncoder) {
        if (!passwordEncoder.matches(rawPassword, this.password)) {
            throw new AuthException(PASSWORD_NOT_MATCH);
        }
    }

}
