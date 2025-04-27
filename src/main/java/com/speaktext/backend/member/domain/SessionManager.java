package com.speaktext.backend.member.domain;

public interface SessionManager {

    String createSession(String memberId);
    String findUserId(String sessionId);
    void expireSession(String sessionId);

}
