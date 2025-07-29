package com.example.demo.login.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
    JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // ✅ 1. 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);
        System.out.println("🔥 JwtAuthenticationFilter 동작함, 추출된 토큰: " + token);
        System.out.println("🔐 요청에서 추출한 토큰: " + token);

        // ✅ 2. 토큰 검증
        if (token != null && jwtTokenProvider.validateToken(token)) {
            System.out.println("✅ 토큰 유효함, 인증 객체 설정 중...");

            // ✅ 3. 인증 객체 생성 및 SecurityContext에 설정
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } else {
            System.out.println("⛔ 토큰이 없거나 유효하지 않음.");
        }

        filterChain.doFilter(request, response);
    }
}
