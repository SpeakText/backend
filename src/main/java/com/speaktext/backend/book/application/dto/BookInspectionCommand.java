package com.speaktext.backend.book.application.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record BookInspectionCommand(
        MultipartFile txtFile,
        String title,
        String description,
        String coverUrl,
        BigDecimal price,
        String identificationNumber
) {}
