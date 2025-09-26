package com.spring.boot.labs.oauth.security.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.spring.boot.labs.oauth.security.entity.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception exception) {
        return new ResponseEntity<>(ApiResponse.builder().message(exception.getMessage()).status("401").build(), HttpStatus.BAD_REQUEST);
    }

}
