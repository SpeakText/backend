package com.speaktext.backend.book.selling_book.application.dto;

public record ReadingProgressResponse(
        Long currentIndex
) {
    public static ReadingProgressResponse from(Long currentIndex) {
        return new ReadingProgressResponse(currentIndex);
    }
}
