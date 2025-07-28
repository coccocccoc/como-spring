package com.example.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.message.dto.MessageDTO;
import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Page<UserDTO> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return userRepository.findAll(pageable).map(this::entityToDto);
    }

    @Override
    public boolean register(UserDTO dto) {
        // 수정된 부분: socialProvider까지 비교
        if (userRepository.findBySocialIdAndSocialProvider(
                dto.getSocialId(),
                dto.getSocialProvider()
        ).isPresent()) {
            return false;
        }

        User user = dtoToEntity(dto);
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDTO read(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        return entityToDto(user);
    }

    @Override
    @Transactional
    public List<MessageDTO> getSentMessages(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        return user.getSentMessages().stream()
                .map(this::messageToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<MessageDTO> getReceivedMessages(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        return user.getReceivedMessages().stream()
                .map(this::messageToDto)
                .collect(Collectors.toList());
    }

    MessageDTO messageToDto(com.example.demo.message.entity.Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .senderId(message.getSender().getUserId())
                .receiverId(message.getReceiver().getUserId())
                .title(message.getTitle())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }

    @Override
    @Transactional
    public void updateProfileImage(Long userId, String imageData) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        user.setProfileImage(imageData);
        userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void updateEmail(Long userId, String email) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        user.setEmail(email);
        userRepository.save(user);
    }

}
