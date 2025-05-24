package com.speaktext.backend.book.selling_book.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record ReadingProgressRequest(
        @NotNull Long currentIndex
) {
}
