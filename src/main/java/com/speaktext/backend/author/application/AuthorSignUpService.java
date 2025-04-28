package com.speaktext.backend.author.application;

import com.speaktext.backend.author.application.dto.SignUpSuccessResponse;
import com.speaktext.backend.author.domain.Author;
import com.speaktext.backend.author.domain.repository.AuthorRepository;
import com.speaktext.backend.common.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorSignUpService {

    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpSuccessResponse signUp(String id, String name, String email, String password) {
        Author author = Author.of(id, name, email, password, passwordEncoder);
        Author saved = authorRepository.save(author);
        return new SignUpSuccessResponse(saved.getAuthorId());
    }

}
