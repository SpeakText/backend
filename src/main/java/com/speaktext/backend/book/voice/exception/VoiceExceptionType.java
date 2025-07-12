package com.speaktext.backend.book.voice.exception;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum VoiceExceptionType implements BaseExceptionType {

    NO_VOICE(NOT_FOUND, "정해진 보이스가 없습니다."),
    MERGED_VOICE_NOT_FOUND(NOT_FOUND, "병합된 보이스가 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus httpStatus() {
        return null;
    }

    @Override
    public String errorMessage() {
        return "";
    }

}
