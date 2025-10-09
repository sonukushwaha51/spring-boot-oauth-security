package com.spring.boot.labs.oauth.security.service;

import java.util.Date;
import java.util.Map;

import com.spring.boot.labs.oauth.security.AuthUtil;
import com.spring.boot.labs.oauth.security.entity.enumFiles.AuthProviderType;
import com.spring.boot.labs.oauth.security.entity.enumFiles.RoleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.spring.boot.labs.oauth.security.entity.LoginRequest;
import com.spring.boot.labs.oauth.security.entity.LoginResponse;
import com.spring.boot.labs.oauth.security.entity.SignupRequest;
import com.spring.boot.labs.oauth.security.entity.SignupResponse;
import com.spring.boot.labs.oauth.security.entity.User;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenService jwtTokenService;

    private final AuthUtil authUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        String userIdType = LoginRequest.determineUserIdType(loginRequest.getUserName());
        User user = userService.loadUser(loginRequest.getUserName(), userIdType);
        return LoginResponse.builder()
                .accessToken(jwtTokenService.generateToken(user))
                .userId(user.getId())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15).getTime())
                .build();
    }

    public SignupResponse signup(SignupRequest request, RoleType role, AuthProviderType authProviderType, String providerId) {
        User user = userService.loadUser(request.getUserName(), LoginRequest.determineUserIdType(request.getUserName()));
        if (user != null) {
            throw new RuntimeException("User already exists");
        }
        user = userService.createCustomer(request, role, authProviderType, providerId);
        return SignupResponse.builder()
                .userId(user.getUsername())
                .userName(request.getUserName())
                .build();

    }

    public ResponseEntity<LoginResponse> handleOAuthLogin(String registrationId, OAuth2User oAuth2User) {
        // Get provider Type
        AuthProviderType authProviderType = authUtil.retrieveProviderType(registrationId);

        // Get provider Id
        String providerId = authUtil.retrieveProviderId(registrationId, oAuth2User);

        // Check user exists. If yes, login and return token. If user does not exist, signup then login and return token
        String userName = authUtil.retrieveUserName(registrationId, oAuth2User);
        User user = userService.loadUser(userName, LoginRequest.determineUserIdType(userName));
        if (user != null) {
            LoginRequest loginRequest = LoginRequest.builder().userName(userName).build();
            return new ResponseEntity<>(login(loginRequest), HttpStatus.OK);
        }

        Map<String, String> userAttributes = authUtil.getOtherAttributes(authProviderType, oAuth2User);
        SignupRequest signupRequest = SignupRequest.builder()
                .userName(userName)
                .email(userAttributes.get("email"))
                .firstName(userAttributes.get("firstName"))
                .lastName(userAttributes.get("lastName"))
                .build();
        signup(signupRequest, RoleType.USER, authProviderType, providerId);
        LoginRequest loginRequest = LoginRequest.builder().userName(userName).build();
        return new ResponseEntity<>(login(loginRequest), HttpStatus.OK);
    }
}
