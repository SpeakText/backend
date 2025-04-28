package com.speaktext.backend.auth.presentation.validator;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.stream.Stream;

public class AdminRequestValidator {

    private AdminRequestValidator() {
    }

    public static void validate(HttpServletRequest request) {
        getUrlParameters(request)
                .forEach(AdminRequestValidator::validateSafeUrl);
    }

    private static Stream<String> getUrlParameters(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .filter(entry -> entry.getKey().toLowerCase().contains("url"))
                .flatMap(entry -> Arrays.stream(entry.getValue()));
    }

    private static void validateSafeUrl(String url) {
        UrlSafetyValidator.validate(url);
    }

}
