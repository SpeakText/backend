package com.speaktext.backend.auth.exception;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum AuthExceptionType implements BaseExceptionType {

    NO_SUCH_MEMBER(NOT_FOUND, "해당 유저를 찾을 수가 없습니다."),
    NO_SUCH_AUTHOR(NOT_FOUND, "해당 작가를 찾을 수가 없습니다."),
    PASSWORD_NOT_MATCH(FORBIDDEN, "비밀번호가 일치하지 않습니다."),
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
