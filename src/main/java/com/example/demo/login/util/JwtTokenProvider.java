package com.example.demo.login.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Base64;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

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

    // ✅ 토큰 생성
    public String createToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();
    }
    
    // ✅ 토큰에서 userId(subject) 추출
    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // subject == userId
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("유효하지 않은 JWT 토큰입니다.", e);
        }
    }
    
    public Long extractUserId(String token) {
    	System.out.println("🔍 받은 Authorization 헤더: " + token);
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Claims claims = Jwts.parser()
            .setSigningKey(secretKey) // 보통 Base64 인코딩된 secret key
            .parseClaimsJws(token)
            .getBody();

        System.out.println("✅ 파싱된 사용자 ID: " + claims.getSubject());
        return Long.parseLong(claims.getSubject()); // subject에 userId가 저장되어 있다고 가정
    }
    
    @Autowired
    private UserRepository userRepository;

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(hmacKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(hmacKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        Long userId = Long.valueOf(getUserId(token));
        User user = userRepository.findById(userId).orElseThrow();
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(user, "", null);
    }
}
