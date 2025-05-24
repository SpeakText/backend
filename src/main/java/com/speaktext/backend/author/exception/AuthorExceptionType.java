package com.speaktext.backend.author.exception;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AuthorExceptionType implements BaseExceptionType {

    ALREADY_EXISTS_AUTHOR_ID(CONFLICT, "이미 존재하는 작가 ID입니다."),
    AUTHOR_NOT_FOUND(NOT_FOUND, "작가를 찾을 수 없습니다."),
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
