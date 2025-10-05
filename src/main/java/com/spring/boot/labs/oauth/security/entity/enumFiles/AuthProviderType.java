package com.spring.boot.labs.oauth.security.entity.enumFiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthProviderType {

    GOOGLE("google"),
    GITHUB("github"),
    EMAIL("email"),
    META("meta");

    private final String providerType;
}
