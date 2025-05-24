package com.speaktext.backend.book.inspection.application.mapper;

import com.speaktext.backend.book.inspection.application.dto.BookInspectionCommand;
import com.speaktext.backend.book.inspection.presentation.dto.BookInspectionRequest;
import org.springframework.stereotype.Component;

@Component
public class BookInspectionMapper {

    public BookInspectionCommand toCommand(BookInspectionRequest request, String coverUrl) {
        return new BookInspectionCommand(
                request.txtFile(),
                request.title(),
                request.description(),
                coverUrl,
                request.price(),
                request.identificationNumber()
        );
    }

}
