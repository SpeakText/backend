package com.speaktext.backend.infra.session;

import com.speaktext.backend.auth.SessionManager;
import com.speaktext.backend.auth.SessionUser;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySessionManager implements SessionManager {

    private final Map<String, SessionUser> sessions = new ConcurrentHashMap<>();

    @Override
    public void saveSession(String sessionId, SessionUser sessionUser) {
        sessions.put(sessionId, sessionUser);
    }

    @Override
    public SessionUser findSession(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public void expireSession(String sessionId) {
        sessions.remove(sessionId);
    }

}
