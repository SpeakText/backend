package com.speaktext.backend.book.inspection.exception;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum PendingBookExceptionType implements BaseExceptionType {

    PENDING_BOOK_NOT_FOUND(HttpStatus.NOT_FOUND, "대기 도서를 찾을 수 없습니다."),
    PENDING_BOOK_NOT_IN_PENDING_STATUS(HttpStatus.BAD_REQUEST,  "대기 도서가 Pending 상태가 아닙니다."),
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
