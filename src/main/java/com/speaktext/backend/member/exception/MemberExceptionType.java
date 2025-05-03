package com.speaktext.backend.member.exception;

import static org.springframework.http.HttpStatus.CONFLICT;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum MemberExceptionType implements BaseExceptionType {

    ALREADY_EXISTS_MEMBER_ID(CONFLICT, "이미 존재하는 멤버 ID입니다."),;

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
