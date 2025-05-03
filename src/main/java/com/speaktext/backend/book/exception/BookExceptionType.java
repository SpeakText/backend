package com.speaktext.backend.book.exception;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum BookExceptionType implements BaseExceptionType {

    NO_PENDING_BOOK(NOT_FOUND, "해당되는 Pending Book이 존재하지 않습니다."),
    NO_APPROVED_PENDING_BOOK(FORBIDDEN, "스크립트를 변환할 권한이 있는 Pending Book이 아닙니다."),
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
