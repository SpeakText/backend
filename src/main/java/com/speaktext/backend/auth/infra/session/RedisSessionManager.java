package com.speaktext.backend.auth.infra.session;

import com.speaktext.backend.auth.SessionManager;
import com.speaktext.backend.auth.SessionUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
@Primary
public class RedisSessionManager implements SessionManager {

    private final RedisTemplate<String, SessionUser> redisTemplate;

    @Autowired
    public RedisSessionManager(RedisTemplate<String, SessionUser> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveSession(String sessionId, SessionUser sessionUser) {
        Duration expiration = Duration.ofMinutes(120);
        redisTemplate.opsForValue().set(sessionId, sessionUser, expiration);
    }

    @Override
    public SessionUser findSession(String sessionId) {
        return redisTemplate.opsForValue().get(sessionId);
    }

    @Override
    public void expireSession(String sessionId) {
        redisTemplate.delete(sessionId);
    }
}
