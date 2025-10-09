package com.spring.boot.labs.oauth.security;

import com.spring.boot.labs.oauth.security.entity.enumFiles.AuthProviderType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
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
                return String.valueOf(oAuth2User.getAttribute("sub"));
            }
            case "github", "facebook", "linkedin" -> {
                Integer providerId = oAuth2User.getAttribute("id");
                return String.valueOf(providerId);
            }
            default -> throw new IllegalArgumentException("Invalid Authentication Provider");
        }
    }

    public String retrieveUserName(String registrationId, OAuth2User oAuth2User) {
        switch (registrationId) {
            case "github" -> {
                return oAuth2User.getAttribute("login");
            }
            case "google" -> {
                return oAuth2User.getAttribute("username");
            }
            default -> throw new IllegalArgumentException("Could not retrieve username");
        }
    }

    public Map<String, String> getOtherAttributes(AuthProviderType authProviderType, OAuth2User oAuth2User) {
        Map<String, String> map = new LinkedHashMap<>();
        switch (authProviderType) {
            case GITHUB -> {
                map.put("email", oAuth2User.getAttribute("email"));
                map.put("login", oAuth2User.getAttribute("login"));
                map.put("firstName", oAuth2User.getAttribute("name"));
            }
            case GOOGLE -> {
                map.put("email", oAuth2User.getAttribute("email"));
            }
        }
        return map;
    }
}
