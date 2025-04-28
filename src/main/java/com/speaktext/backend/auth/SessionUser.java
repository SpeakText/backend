package com.speaktext.backend.auth;

import com.speaktext.backend.common.type.UserType;

public record SessionUser(
        Long userId,
        UserType userType
) {}
