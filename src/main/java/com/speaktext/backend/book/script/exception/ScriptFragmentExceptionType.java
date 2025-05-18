package com.speaktext.backend.book.script.exception;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ScriptFragmentExceptionType implements BaseExceptionType {

    SCRIPT_FRAGMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 스크립트 요소가 없습니다."),
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
