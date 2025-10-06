package com.spring.boot.labs.oauth.security;

import com.spring.boot.labs.oauth.security.entity.enumFiles.AuthProviderType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class AuthUtil {


    public AuthProviderType retrieveProviderType(String registrationId) {

        switch (registrationId) {
            case "google" -> {
                return AuthProviderType.GOOGLE;
            }
            case "github" -> {
                return AuthProviderType.GITHUB;
            }
            case "facebook" -> {
                return AuthProviderType.META;
            }
            case "linkedin" -> {
                return AuthProviderType.LINKEDIN;
            }
            default -> throw new IllegalArgumentException("Invalid Authentication Provider");
        }
    }

    public String retrieveProviderId(String registrationId, OAuth2User oAuth2User) {
        switch (registrationId) {
            case "google" -> {
                return oAuth2User.getAttribute("sub");
            }
            case "github", "facebook", "linkedin" -> {
                return oAuth2User.getAttribute("id");
            }
            default -> throw new IllegalArgumentException("Invalid Authentication Provider");
        }
    }

    public String retrieveUserName(String registrationId, OAuth2User oAuth2User) {
        return null;
    }
}
