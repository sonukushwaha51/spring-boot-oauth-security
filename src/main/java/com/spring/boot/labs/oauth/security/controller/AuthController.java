package com.spring.boot.labs.oauth.security.controller;

import com.spring.boot.labs.oauth.security.entity.enumFiles.RoleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.labs.oauth.security.entity.LoginRequest;
import com.spring.boot.labs.oauth.security.entity.LoginResponse;
import com.spring.boot.labs.oauth.security.entity.SignupRequest;
import com.spring.boot.labs.oauth.security.entity.SignupResponse;
import com.spring.boot.labs.oauth.security.service.AuthService;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/customer-login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/doctor-login")
    public ResponseEntity<LoginResponse> doctorLogin(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/user/customer-signup")
    public ResponseEntity<SignupResponse> userSignup(@RequestBody SignupRequest signupRequest) {
        log.info("Creating user: {}", signupRequest.getUserName());
        return new ResponseEntity<>(authService.signup(signupRequest, RoleType.USER), HttpStatus.OK);
    }

    @PostMapping("/admin/customer-signup")
    public ResponseEntity<SignupResponse> adminSignup(@RequestBody SignupRequest signupRequest) {
        log.info("Creating user: {}", signupRequest.getUserName());
        return new ResponseEntity<>(authService.signup(signupRequest, RoleType.ADMIN), HttpStatus.OK);
    }


}
