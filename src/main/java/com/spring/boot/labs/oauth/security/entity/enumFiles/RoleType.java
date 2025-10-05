package com.spring.boot.labs.oauth.security.entity.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleType {

    ADMIN("ADMIN"),
    DOCTOR("DOCTOR"),
    PATIENT("PATIENT"),
    USER("USER");

    private final String role;
}
