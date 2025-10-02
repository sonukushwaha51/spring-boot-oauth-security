package com.spring.boot.labs.oauth.security.entity.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleType {

    ADMIN("ROLE_ADMIN"),
    DOCTOR("ROLE_DOCTOR"),
    PATIENT("ROLE_PATIENT"),
    USER("ROLE_USER");

    private final String role;
}
