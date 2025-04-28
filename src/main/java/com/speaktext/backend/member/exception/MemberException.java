package com.speaktext.backend.member.exception;

import com.speaktext.backend.common.exception.BaseException;
import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberException extends BaseException {
    private final MemberExceptionType memberExceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return memberExceptionType;
    }
}
