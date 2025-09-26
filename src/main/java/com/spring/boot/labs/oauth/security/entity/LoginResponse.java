package com.spring.boot.labs.oauth.security.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {

    @JsonProperty("access_token")
    private String accessToken;

    private String userId;

    private Long expiration;

    @Builder.Default
    private Date issuedAt = new Date(System.currentTimeMillis());

}
