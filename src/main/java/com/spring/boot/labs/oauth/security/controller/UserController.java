package com.spring.boot.labs.oauth.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.labs.oauth.security.entity.User;
import com.spring.boot.labs.oauth.security.service.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/home")
    public ResponseEntity<String> renderHomePage() {
        return new ResponseEntity<>("Welcome to Home page. This application is for spring security practise.", HttpStatus.OK);
    }
    
    @PostMapping("/customer-signup")
    public ResponseEntity<String> createCustomer(@RequestBody User user) {
        userService.createCustomer(user);
        return new ResponseEntity<String>("User created", HttpStatus.CREATED);
    }

}
