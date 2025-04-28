package com.speaktext.backend.auth.exception;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
public enum AuthExceptionType implements BaseExceptionType {

    NO_SUCH_MEMBER(NOT_FOUND, "해당 유저를 찾을 수가 없습니다."),
    NO_SUCH_AUTHOR(NOT_FOUND, "해당 작가를 찾을 수가 없습니다."),
    PASSWORD_NOT_MATCH(FORBIDDEN, "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED_MEMBER(FORBIDDEN, "멤버 권한이 아닙니다."),
    UNAUTHORIZED_AUTHOR(FORBIDDEN, "작가 권한이 아닙니다."),
    NO_SESSION_HEADER(UNAUTHORIZED, "세션 헤더가 없습니다."),
    NO_SESSION_KEY(UNAUTHORIZED, "유효하지 않은 세션입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return httpStatus;
    }

    @Override
    public String errorMessage() {
        return message;
    }

}
