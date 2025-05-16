package com.speaktext.backend.book.script.exception;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum ScriptExceptionType implements BaseExceptionType {

    SCRIPT_NOT_FOUND(NOT_FOUND, "스크립트를 찾을 수 없습니다."),
    SCRIPT_FRAGMENT_NOT_FOUND(NOT_FOUND, "해당 스크립트 요소가 없습니다."),
    CHARACTER_NOT_FOUND(NOT_FOUND, "등장인물을 찾을 수 없습니다."),
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
