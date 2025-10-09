package com.spring.boot.labs.oauth.security.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.labs.oauth.security.service.AuthService;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@EnableEncryptableProperties
@Slf4j
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthFilter;

    @Value("${jasypt.encryptor.password}")
    private String encryptorPassword;

    @Value("${jasypt.encryptor.algorithm}")
    private String encryptorAlgorithm;

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(encryptorPassword);
        encryptor.setAlgorithm(encryptorAlgorithm);
        return encryptor;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler savedRequestAwareAuthenticationSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/home"); // or any fallback URL
        handler.setAlwaysUseDefaultTargetUrl(false); // only use default if no saved request
        return handler;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthService authService, ObjectMapper objectMapper) throws Exception {
        try {
            OAuthSuccessHandler oAuthSuccessHandler = new OAuthSuccessHandler(savedRequestAwareAuthenticationSuccessHandler(), authService, objectMapper);
            return httpSecurity
                        .csrf(AbstractHttpConfigurer::disable)
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                    // Stores request in HttpSessionRequestCache and when login succeeds, it uses this cache to redirect to original url.
                        .authorizeHttpRequests(request -> request
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                        .oauth2Login(oauth -> oauth.successHandler(oAuthSuccessHandler).failureHandler((this::oauthFailureHandler)))
                        .build();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    // To return access token on SSO or OAuth login
//    public void oauthSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws Exception {
//        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
//        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();
//
//        // Get registrationId
//        String registrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
//        ResponseEntity<LoginResponse> oauth2Response = authService.handleOAuthLogin(registrationId, oAuth2User);
//        response.setStatus(oauth2Response.getStatusCode().value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().write(objectMapper.writeValueAsString(oauth2Response));
//        response.sendRedirect(request.getRequestURI());
//    }

    public void oauthFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.error("Oauth2 Server error: {}, response: {}", exception.getMessage(), response.toString());
    }

}
