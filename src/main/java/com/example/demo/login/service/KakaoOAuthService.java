package com.example.demo.login.service;

import com.example.demo.login.dto.KakaoTokenResponse;
import com.example.demo.login.dto.KakaoUserInfo;
import com.example.demo.login.util.JwtTokenProvider;
import com.example.demo.user.entity.User;
import com.example.demo.user.entity.User.provider;
import com.example.demo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final WebClient webClient;

    @Transactional
    public Map<String, String> kakaoLogin(String code) {
        // 1. access token 받기
        String accessToken = getAccessToken(code);
        System.out.println("accessToken: " + accessToken);

        // 2. 사용자 정보 가져오기
        KakaoUserInfo userInfo = getUserInfo(accessToken);
        System.out.println("userInfo: " + userInfo);
        System.out.println("userInfo.getId(): " + userInfo.getId());
        System.out.println("userInfo.getNickname(): " + userInfo.getNickname());
        
        String email = userInfo.getEmail();
        System.out.println("userInfo.getEmail(): " + email);

        // 3. 닉네임 null-safe 처리
        String nickname = userInfo.getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = "카카오사용자" + userInfo.getId();
        }
        String finalNickname = nickname;

        // 4. 사용자 저장 or 조회
        User user = userRepository.findBySocialIdAndSocialProvider(
                String.valueOf(userInfo.getId()),
                provider.KAKAO
        ).orElseGet(() -> {
            User newUser = User.builder()
                    .socialId(String.valueOf(userInfo.getId()))
                    .nickname(finalNickname)
                    .email(email)                 // ✅ 추가
                    .profileImage(null) 
                    .role("USER")
                    .socialProvider(provider.KAKAO)
                    .build();
            return userRepository.save(newUser);
        });

        System.out.println("DB에 저장된 user: " + user);
        System.out.println("user.getNickname(): " + user.getNickname());

        // 5. JWT 토큰 발급
        String jwt = jwtTokenProvider.createToken(String.valueOf(user.getUserId()));
        System.out.println("JWT 토큰 발급 완료");

        return Map.of(
                "token", jwt,
                "nickname", user.getNickname()
        );
    }

    private String getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        KakaoTokenResponse response = webClient.post()
                .uri(tokenUrl)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .bodyValue("grant_type=authorization_code" +
                        "&client_id=" + clientId +
                        "&redirect_uri=" + redirectUri +
                        "&code=" + code)
                .retrieve()
                .bodyToMono(KakaoTokenResponse.class)
                .block();

        return response.getAccessToken();
    }

    private KakaoUserInfo getUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        return webClient.get()
                .uri(userInfoUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .bodyToMono(KakaoUserInfo.class)
                .block();
    }
}
