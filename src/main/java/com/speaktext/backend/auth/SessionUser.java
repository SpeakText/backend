package com.speaktext.backend.auth;

public record SessionUser(
        Long userId,
        UserType userType
) {}
