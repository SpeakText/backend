package com.speaktext.backend.book.inspection.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record BookInspectionRequest(
    @NotNull MultipartFile txtFile,
    @NotBlank String title,
    @NotBlank String description,
    @NotNull MultipartFile coverImage,
    @NotNull BigDecimal price,
    @NotNull String identificationNumber
) {}
