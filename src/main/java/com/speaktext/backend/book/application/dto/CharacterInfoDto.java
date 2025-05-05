package com.speaktext.backend.book.application.dto;

import com.speaktext.backend.book.domain.CharacterInfo;

public record CharacterInfoDto(String name, String description, String characterId) {

    public CharacterInfo toDomain() {
        return new CharacterInfo(name, description, characterId);
    }

    public static CharacterInfoDto from(CharacterInfo entity) {
        return new CharacterInfoDto(entity.getName(), entity.getDescription(), entity.getCharacterId());
    }

}
