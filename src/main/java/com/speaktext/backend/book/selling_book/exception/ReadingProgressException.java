package com.speaktext.backend.book.selling_book.exception;

import com.speaktext.backend.common.exception.BaseException;
import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReadingProgressException extends BaseException {

    private final ReadingProgressExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }

}
