package com.speaktext.backend.book.exception;

import com.speaktext.backend.common.exception.BaseException;
import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PendingBookException extends BaseException {

    private final PendingBookExceptionType pendingBookExceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return pendingBookExceptionType;
    }
}
