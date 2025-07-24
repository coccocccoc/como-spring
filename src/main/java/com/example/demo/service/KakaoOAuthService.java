package com.example.demo.service;

import com.example.demo.dto.KakaoTokenResponse;
import com.example.demo.dto.KakaoUserInfo;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoOAuthService {

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    private final WebClient webClient = WebClient.builder().build();

    public Map<String, String> kakaoLogin(String code) {
        // 1. access token 받기
        String accessToken = getAccessToken(code);

        // 2. 사용자 정보 가져오기
        KakaoUserInfo userInfo = getUserInfo(accessToken);

        // 3. 회원가입 또는 기존 회원 조회
        User user = userRepository.findByKakaoId(userInfo.getId())
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setKakaoId(userInfo.getId());
                    newUser.setNickname(userInfo.getNickname());
                    newUser.setEmail(userInfo.getEmail());
                    return userRepository.save(newUser);
                });

        // 4. JWT 토큰 생성
        String jwt = jwtTokenProvider.createToken(user.getId().toString());

        // 5. 프론트에 토큰 반환
        return Map.of("token", jwt);
    }

    private String getAccessToken(String code) {
        String tokenUrl = "https://kauth.kakao.com/oauth/token";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", clientId);
        formData.add("redirect_uri", redirectUri);
        formData.add("code", code);

        KakaoTokenResponse response = webClient.post()
                .uri(tokenUrl)
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .bodyValue(formData)
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
