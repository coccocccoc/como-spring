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
                .region(user.getRegion())
                .age(user.getAge())
                .role(user.getRole())
                .socialProvider(user.getSocialProvider())
                .build();
    }

    // dto → entity 변환
    default User dtoToEntity(UserDTO dto) {
        User.UserBuilder builder = User.builder()
            .socialId(dto.getSocialId())
            .nickname(dto.getNickname())
            .region(dto.getRegion())
            .age(dto.getAge())
            .role(dto.getRole())
            .socialProvider(dto.getSocialProvider());

        if (dto.getUserId() != null) {
            builder.userId(dto.getUserId());
        }

        return builder.build();
    }

    // 전체 회원 목록 (페이징)
    Page<UserDTO> getList(int page);

    // 회원 등록
    boolean register(UserDTO dto);

    // 단일 회원 조회
    UserDTO read(Long userId);
    
    List<MessageDTO> getSentMessages(Long userId);
    
    List<MessageDTO> getReceivedMessages(Long userId);
}
