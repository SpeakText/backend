package com.speaktext.backend.auth.exception;

import com.speaktext.backend.common.exception.BaseException;
import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthException extends BaseException {

    private final AuthExceptionType authExceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return authExceptionType;
    }

}
