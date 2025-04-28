package com.speaktext.backend.auth;

import com.speaktext.backend.auth.exception.AuthException;
import com.speaktext.backend.auth.exception.AuthExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.speaktext.backend.auth.exception.AuthExceptionType.*;

@Component
@RequiredArgsConstructor
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionManager sessionManager;
    private final HttpServletRequest request;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Member.class)
                && Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) {
        String sessionId = request.getHeader("SESSIONID");
        SessionUser sessionUser = validateSession(sessionId);
        validateMemberRole(sessionUser);
        return sessionUser.userId();
    }

    private SessionUser validateSession(String sessionId) {
        if (sessionId == null) {
            throw new AuthException(NO_SESSION_HEADER);
        }

        SessionUser sessionUser = sessionManager.findSession(sessionId);
        if (sessionUser == null) {
            throw new AuthException(NO_SESSION_KEY);
        }

        return sessionUser;
    }

    private void validateMemberRole(SessionUser sessionUser) {
        if (sessionUser.userType() != UserType.MEMBER) {
            throw new AuthException(UNAUTHORIZED_MEMBER);
        }
    }

}
