package com.speaktext.backend.author.exception;

import com.speaktext.backend.common.exception.BaseException;
import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorException extends BaseException {
    private final AuthorExceptionType authorExceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return authorExceptionType;
    }
}
