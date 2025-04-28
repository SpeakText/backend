package com.speaktext.backend.auth.infra.session;

import com.speaktext.backend.auth.SessionUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;

import static org.mockito.Mockito.*;

@DisplayName("Redis 세션 매니저 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RedisSessionManagerTest {

    @Mock
    private RedisTemplate<String, SessionUser> redisTemplate;

    @Mock
    private ValueOperations<String, SessionUser> valueOperations;

    @InjectMocks
    private RedisSessionManager redisSessionManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void 세션_저장에_성공한다() {
        // Given
        String sessionId = "testSessionId";
        SessionUser sessionUser = new SessionUser(1L, null);
        Duration expiration = Duration.ofMinutes(120);

        // When
        redisSessionManager.saveSession(sessionId, sessionUser);

        // Then
        verify(valueOperations).set(sessionId, sessionUser, expiration);
    }

    @Test
    public void 세션_검색에_성공한다() {
        // Given
        String sessionId = "testSessionId";
        SessionUser sessionUser = new SessionUser(1L, null);
        when(valueOperations.get(sessionId)).thenReturn(sessionUser);

        // When
        SessionUser result = redisSessionManager.findSession(sessionId);

        // Then
        verify(valueOperations).get(sessionId);
        assert(result == sessionUser);
    }

    @Test
    public void 세션_만료에_성공한다() {
        // Given
        String sessionId = "testSessionId";

        // When
        redisSessionManager.expireSession(sessionId);

        // Then
        verify(redisTemplate).delete(sessionId);
    }
}
