package com.speaktext.backend.book.application.mapper;

import com.speaktext.backend.book.application.dto.BookInspectionCommand;
import com.speaktext.backend.book.presentation.dto.BookInspectionRequest;
import org.springframework.stereotype.Component;

@Component
public class BookInspectionMapper {

    public BookInspectionCommand toCommand(BookInspectionRequest request) {
        return new BookInspectionCommand(
                request.txtFile(),
                request.title(),
                request.description(),
                request.coverUrl(),
                request.price(),
                request.identificationNumber()
        );
    }

}
