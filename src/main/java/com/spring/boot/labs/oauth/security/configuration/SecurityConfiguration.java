package com.spring.boot.labs.oauth.security.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class SecurityConfiguration {

    // @Override
    // public List<Filter> getFilters() {
    //     return new ArrayList<>();
    // }

    // @Override
    // public boolean matches(HttpServletRequest request) {
        
    //     return false;
    // }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
