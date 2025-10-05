package com.spring.boot.labs.oauth.security.service;

import java.util.Date;

import com.spring.boot.labs.oauth.security.entity.enumFiles.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.boot.labs.oauth.security.entity.LoginRequest;
import com.spring.boot.labs.oauth.security.entity.LoginResponse;
import com.spring.boot.labs.oauth.security.entity.SignupRequest;
import com.spring.boot.labs.oauth.security.entity.SignupResponse;
import com.spring.boot.labs.oauth.security.entity.User;

@Service
@Slf4j
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenService jwtTokenService;

    public LoginResponse login(LoginRequest loginRequest) {
        String userIdType = LoginRequest.determineUserIdType(loginRequest.getUserName());
        User user = userService.loadUser(loginRequest.getUserName(), userIdType);
        return LoginResponse.builder()
                            .accessToken(jwtTokenService.generateToken(user))
                            .userId(user.getId())
                            .expiration(new Date(System.currentTimeMillis() + 1000*60*15).getTime())
                            .build();
    }

    public SignupResponse signup(SignupRequest request, RoleType role) {
        User user = userService.loadUser(request.getUserName(), LoginRequest.determineUserIdType(request.getUserName()));
        if (user != null) {
            throw new RuntimeException("User already exists");
        }
        user = userService.createCustomer(request, role);
        return SignupResponse.builder()
                            .userId(user.getId())
                            .userName(request.getUserName())
                            .build();

    }

}
