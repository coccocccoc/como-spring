package com.example.demo.login.controller;

import com.example.demo.login.service.KakaoOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {

    private final KakaoOAuthService kakaoOAuthService;

    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam("code") String code) {
    	
        Map<String, String> tokenMap = kakaoOAuthService.kakaoLogin(code);
        return ResponseEntity.ok(tokenMap);
    }
}
