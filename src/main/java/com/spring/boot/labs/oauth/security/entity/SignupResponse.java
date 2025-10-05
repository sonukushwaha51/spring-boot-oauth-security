package com.spring.boot.labs.oauth.security.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignupResponse {

    private String userName;

    private String userId;
    
}
