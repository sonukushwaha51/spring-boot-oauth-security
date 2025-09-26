package com.spring.boot.labs.oauth.security.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.spring.boot.labs.oauth.security.entity.LoginRequest;
import com.spring.boot.labs.oauth.security.entity.User;
import com.spring.boot.labs.oauth.security.service.JwtTokenService;
import com.spring.boot.labs.oauth.security.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        log.info("Checking auth for Incoming request");

        String autheHeader = request.getHeader("AUTHORIZATION");
        if (autheHeader == null || !autheHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String accessToken = autheHeader.split("Bearer ")[1];

        String userName = jwtTokenService.extractUserName(accessToken);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userService.loadUser(userName, LoginRequest.determineUserIdType(userName));
            if (user == null) {
                throw new UsernameNotFoundException("User does not exist: "+userName);
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(userName, accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        
        filterChain.doFilter(request, response);
        
    }

}
