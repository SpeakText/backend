package com.speaktext.backend.author.application;

import com.speaktext.backend.auth.application.SessionService;
import com.speaktext.backend.auth.exception.AuthException;
import com.speaktext.backend.author.application.dto.SignInSuccessResponse;
import com.speaktext.backend.author.domain.Author;
import com.speaktext.backend.author.domain.repository.AuthorRepository;
import com.speaktext.backend.common.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.speaktext.backend.common.type.UserType.AUTHOR;
import static com.speaktext.backend.auth.exception.AuthExceptionType.NO_SUCH_AUTHOR;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final PasswordEncoder passwordEncoder;
    private final SessionService sessionService;
    private final AuthorRepository authorRepository;

    public SignInSuccessResponse signIn(String id, String rawPassword) {
        Author author = authorRepository.findByIdentifier(id)
                .orElseThrow(() -> new AuthException(NO_SUCH_AUTHOR));
        author.isPasswordMatch(rawPassword, passwordEncoder);
        String sessionId = sessionService.createSession(author.getAuthorId(), AUTHOR);
        return new SignInSuccessResponse(sessionId);
    }

    public void signOut(String sessionId) {
        sessionService.expireSession(sessionId);
    }

}
