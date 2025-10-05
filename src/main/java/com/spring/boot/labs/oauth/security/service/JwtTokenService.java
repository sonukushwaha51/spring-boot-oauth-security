package com.spring.boot.labs.oauth.security.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.spring.boot.labs.oauth.security.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Service
@Getter
public class JwtTokenService {

    @Value("${spring.jwt.secret.key}")
    private String secretKey;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("role", user.getAuthorities())
                .expiration(new Date(System.currentTimeMillis() + 60*1000*10))
                .issuedAt(new Date())
                .signWith(getSecretKey())
                .compact();
    }

    public String extractUserName(String accessToken) {
        Claims claims = Jwts.parser()
                            .verifyWith(getSecretKey())
                            .build()
                            .parseSignedClaims(accessToken)
                            .getPayload();

        return claims.getSubject();
    }

}
