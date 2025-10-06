package com.spring.boot.labs.oauth.security.configuration;

import com.spring.boot.labs.oauth.security.entity.LoginResponse;
import com.spring.boot.labs.oauth.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@Slf4j
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;

    private final AuthService authService;

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
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        // Get registrationId
        String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        ResponseEntity<LoginResponse> oauth2Response = authService.handleOAuthLogin(registrationId, oAuth2User);
    }

    public void oauthFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("Oauth2 Server error: {}", exception.getMessage());
    }

}
