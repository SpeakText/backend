package com.speaktext.backend.book.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record BookInspectionRequest(
    @NotNull MultipartFile txtFile,
    @NotBlank String title,
    @NotBlank String description,
    @NotBlank String coverUrl,
    @NotNull BigDecimal price,
    @NotNull String identificationNumber
) {}
