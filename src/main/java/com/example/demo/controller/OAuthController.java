package com.example.demo.controller;

import com.example.demo.service.KakaoOAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
public class OAuthController {

    private final KakaoOAuthService kakaoOAuthService;

    @PostMapping("/kakao")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code) {
        Map<String, String> tokenMap = kakaoOAuthService.kakaoLogin(code);
        return ResponseEntity.ok(tokenMap);
    }
}
