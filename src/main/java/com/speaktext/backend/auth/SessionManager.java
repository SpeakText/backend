package com.speaktext.backend.auth;

public interface SessionManager {

    void saveSession(String sessionId, SessionUser sessionUser);
    SessionUser findSession(String sessionId);
    void expireSession(String sessionId);

}
