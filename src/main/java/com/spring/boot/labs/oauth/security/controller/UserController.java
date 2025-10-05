package com.spring.boot.labs.oauth.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.spring.boot.labs.oauth.security.service.UserService;

@RestController
@RequestMapping("/public")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public ResponseEntity<String> renderHomePage() {
        return new ResponseEntity<>("Welcome to Home page. This application is for spring security practise.", HttpStatus.OK);
    }

}
