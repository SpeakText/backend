package com.speaktext.backend.book.voice.exception;

import com.speaktext.backend.common.exception.BaseException;
import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VoiceException extends BaseException {

    private final VoiceExceptionType exceptionType;

    @Override
    public BaseExceptionType exceptionType() {
        return exceptionType;
    }

}
