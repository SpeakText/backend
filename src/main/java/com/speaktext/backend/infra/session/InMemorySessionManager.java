package com.speaktext.backend.infra.session;

import com.speaktext.backend.member.domain.SessionManager;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemorySessionManager implements SessionManager {

    private final Map<String, String> sessions = new ConcurrentHashMap<>();

    @Override
    public String createSession(String memberId) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, memberId);
        return sessionId;
    }

    @Override
    public String findUserId(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public void expireSession(String sessionId) {
        sessions.remove(sessionId);
    }

}
