package com.speaktext.backend.book.selling_book.exception;

import com.speaktext.backend.common.exception.BaseExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
public enum SellingBookExceptionType implements BaseExceptionType {

    NO_MERGED_VOICE(NOT_FOUND, "병합된 음성이 존재하지 않습니다."),
    SELLING_BOOK_NOT_FOUND(NOT_FOUND, "판매 중인 책을 찾을 수 없습니다.")
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
