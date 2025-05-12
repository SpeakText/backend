package com.speaktext.backend.book.script.application.dto;

import com.speaktext.backend.book.script.domain.Script;

public record ScriptMetaResponse(

        String identificationNumber,
        String title,
        int totalFragments,
        int fragmentCount,
        boolean isCompleted

) {

    public static ScriptMetaResponse from(Script script) {
        return new ScriptMetaResponse(
                script.getIdentificationNumber(),
                script.getTitle(),
                script.getTotalFragments(),
                script.getFragmentsCount(),
                script.isCompleted()
        );
    }

}
