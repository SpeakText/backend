package com.speaktext.backend.admin.domain;

import com.speaktext.backend.auth.exception.AuthException;
import com.speaktext.backend.common.util.PasswordEncoder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.speaktext.backend.auth.exception.AuthExceptionType.PASSWORD_NOT_MATCH;

@Entity
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long adminId;

    @Column(nullable = false, unique = true, length = 64)
    private String identifier;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false, length = 64)
    private String name;

    public Admin(String identifier, String password, String name) {
        this.identifier = identifier;
        this.password = password;
        this.name = name;
    }

    public static Admin of(String identifier, String name, String password, PasswordEncoder passwordEncoder) {
        return new Admin(identifier, passwordEncoder.encode(password), name);
    }

    public void isPasswordMatch(String rawPassword) {
       if (!this.password.equals(rawPassword)) {
           throw new AuthException(PASSWORD_NOT_MATCH);
       }
    }

}
