package com.spring.boot.labs.oauth.security.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        try {
            return httpSecurity
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                        .oauth2Login(oauth -> oauth.successHandler(this::oauthSuccessHandler).failureHandler((this::oauthFailureHandler)))
                        .build();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public void oauthSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    }

    public void oauthFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("Oauth2 Server error: {}", exception.getMessage());
    }

}
