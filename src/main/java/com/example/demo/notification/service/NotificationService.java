package com.example.demo.notification.service;

import org.springframework.stereotype.Service;

import com.example.demo.notification.dto.NotificationDTO;
import com.example.demo.notification.entity.Notification;
import com.example.demo.notification.repository.NotificationRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
public class NotificationService {

	SimpMessagingTemplate messagingTemplate;
	NotificationRepository repository;
	UserRepository userRepository;

	public NotificationService(SimpMessagingTemplate messagingTemplate, NotificationRepository repository,
			UserRepository userRepository) {
		this.messagingTemplate = messagingTemplate;
		this.repository = repository;
		this.userRepository = userRepository;
	}

	public void sendNotification(long userId, String content, String type, Long targetId) {
	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 존재하지 않습니다."));
	    LocalDateTime now = LocalDateTime.now();

	    Notification notification = Notification.builder()
	            .user(user)
	            .content(content)
	            .createdAt(now)
	            .type(type)
	            .targetId(targetId)
	            .build();

	    repository.save(notification);

	    NotificationDTO dto = NotificationDTO.builder()
	            .content(content)
	            .createdAt(now)
	            .userId(userId)
	            .type(type)
	            .targetId(targetId)
	            .build();
	    
	    System.out.println("💬 알림 전송 대상: " + userId);
        System.out.println("💬 WebSocket 경로: /topic/notifications/" + userId);
        System.out.println("💬 보낼 내용: " + dto.getContent());

	    messagingTemplate.convertAndSend("/topic/notifications/" + userId, dto);

	}
	 
//	 public List<NotificationDTO> getAllNotificationsForUser(int userId) {
//		    List<Notification> notifications = repository.findByUserId(userId); // ❗쿼리 메서드 필요
//		    return notifications.stream()
//		            .map(n -> new NotificationDTO(n.getContent(), n.getCreatedAt(), n.getUser().getId()))
//		            .collect(Collectors.toList());
//		}
}
