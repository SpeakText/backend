package com.speaktext.backend.auth.application;

import com.speaktext.backend.auth.SessionManager;
import com.speaktext.backend.auth.SessionUser;
import com.speaktext.backend.common.type.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionManager sessionManager;

    public String createSession(Long userId, UserType userType) {
        String sessionId = UUID.randomUUID().toString();
        SessionUser sessionUser = new SessionUser(userId, userType);
        sessionManager.saveSession(sessionId, sessionUser);
        return sessionId;
    }

    public SessionUser findSession(String sessionId) {
        return sessionManager.findSession(sessionId);
    }

    public void expireSession(String sessionId) {
        sessionManager.expireSession(sessionId);
    }

}
