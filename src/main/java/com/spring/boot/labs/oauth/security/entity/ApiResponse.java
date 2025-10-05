package com.spring.boot.labs.oauth.security.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse {

    private String status;

    private String message;

    @Builder.Default
    private Date time = new Date(System.currentTimeMillis());
    
}
