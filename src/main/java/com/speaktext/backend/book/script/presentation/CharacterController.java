package com.speaktext.backend.book.script.presentation;

import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.book.script.application.CharacterService;
import com.speaktext.backend.book.script.application.dto.CharacterUpdatedResponse;
import com.speaktext.backend.book.script.presentation.dto.CharactersUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService characterService;

    @PutMapping
    public ResponseEntity<CharacterUpdatedResponse> updateCharacter(
            @Author Long authorId,
            @RequestBody CharactersUpdateRequest request
    ) {
        CharacterUpdatedResponse response = characterService.update(request.toUpdateCommand());
        return ResponseEntity.ok(response);
    }

}
