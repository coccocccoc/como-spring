package com.example.demo.login.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Base64;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key hmacKey;
    private final long expirationTime = 1000 * 60 * 60 * 24; // 24시간

    @PostConstruct
    public void init() {
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        hmacKey = Keys.hmacShaKeyFor(decodedKey);
    }

    public String createToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
