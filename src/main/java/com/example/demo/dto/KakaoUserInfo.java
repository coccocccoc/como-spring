package com.example.demo.dto;

import lombok.Data;

@Data
public class KakaoUserInfo {
    private String id;
    private KakaoAccount kakao_account;

    @Data
    public static class KakaoAccount {
        private String email;
        private Profile profile;

        @Data
        public static class Profile {
            private String nickname;
        }
    }

    public String getEmail() {
        return kakao_account != null ? kakao_account.email : null;
    }

    public String getNickname() {
        return kakao_account != null && kakao_account.profile != null
                ? kakao_account.profile.nickname
                : null;
    }
}
