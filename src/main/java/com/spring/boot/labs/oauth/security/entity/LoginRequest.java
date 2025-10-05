package com.spring.boot.labs.oauth.security.entity;

import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginRequest {

    public static final String EMAIL = "EMAIL";

    public static final String USER_NAME = "USER_NAME";

    private String userName;

    private String password;

    private static final String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static String determineUserIdType(String userName) {
        return Pattern.matches(emailPattern, userName) ? EMAIL : USER_NAME;
    }

}
