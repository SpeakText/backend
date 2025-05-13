package com.speaktext.backend.book.script.presentation;

import com.speaktext.backend.auth.presentation.anotation.Author;
import com.speaktext.backend.book.script.application.CharacterService;
import com.speaktext.backend.book.script.application.dto.CharacterUpdatedResponse;
import com.speaktext.backend.book.script.presentation.dto.CharacterResponse;
import com.speaktext.backend.book.script.presentation.dto.CharactersUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/character")
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping("/{identificationNumber}")
    public ResponseEntity<List<CharacterResponse>> getCharacters(
            @Author Long authorId,
            @PathVariable String identificationNumber
    ) {
        List<CharacterResponse> characters = characterService.getCharacters(identificationNumber);
        return ResponseEntity.ok(characters);
    }

    @PutMapping
    public ResponseEntity<CharacterUpdatedResponse> updateCharacter(
            @Author Long authorId,
            @RequestBody CharactersUpdateRequest request
    ) {
        CharacterUpdatedResponse response = characterService.update(request.toUpdateCommand());
        return ResponseEntity.ok(response);
    }

}
