package com.example.demo.user.service;

import com.example.demo.message.dto.MessageDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.entity.User;

import java.util.List;

import org.springframework.data.domain.Page;

public interface UserService {

    // entity → dto 변환
    default UserDTO entityToDto(User user) {
        return UserDTO.builder()
                .userId(user.getUserId())
                .socialId(user.getSocialId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .role(user.getRole())
                .socialProvider(user.getSocialProvider())
                .profileImage(user.getProfileImage()) 
                .build();
    }

    // dto → entity 변환
    default User dtoToEntity(UserDTO dto) {
        User.UserBuilder builder = User.builder()
            .socialId(dto.getSocialId())
            .nickname(dto.getNickname())
            .email(dto.getEmail())
            .role(dto.getRole())
            .socialProvider(dto.getSocialProvider())
            .profileImage(dto.getProfileImage());

        if (dto.getUserId() != null) {
            builder.userId(dto.getUserId());
        }

        return builder.build();
    }
    
    //프로필 이미지 수정
    void updateProfileImage(Long userId, String imageData);
    
    // 이메일 수정
    void updateEmail(Long userId, String email);

    // 전체 회원 목록 (페이징)
    Page<UserDTO> getList(int page);

    // 회원 등록
    boolean register(UserDTO dto);

    // 단일 회원 조회
    UserDTO read(Long userId);
    
    List<MessageDTO> getSentMessages(Long userId);
    
    List<MessageDTO> getReceivedMessages(Long userId);
}
