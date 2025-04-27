package com.speaktext.backend.member.domain;

import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class PasswordEncoder {

    public String encode(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

    public void matches(String rawPassword, String encodedPassword) {
        BCrypt.checkpw(rawPassword, encodedPassword);
    }

}
