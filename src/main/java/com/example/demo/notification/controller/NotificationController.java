package com.example.demo.notification.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.notification.dto.NotificationDTO;
import com.example.demo.notification.entity.Notification;
import com.example.demo.notification.repository.NotificationRepository;
import com.example.demo.notification.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
@CrossOrigin(origins = "http://localhost:3000")
public class NotificationController {
	
	@Autowired
    NotificationService notificationService;
    
	@Autowired
	NotificationRepository notificationRepository;
    
	public NotificationController(NotificationService notificationService, NotificationRepository notificationRepository) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
    }

	 @PostMapping("/test")
	    public ResponseEntity<String> testSend(@RequestParam("userId") Long userId) {
	        notificationService.sendNotification(userId, "🎉 테스트 알림 도착!", "test", 999L); // type, targetId는 테스트용
	        return ResponseEntity.ok("알림 전송 완료");
	    }
    
    @GetMapping
    public List<NotificationDTO> getUserNotifications(@RequestParam("userId") Long userId) {
    	List<Notification> list = notificationRepository.findByUser_userId(userId);

    	return list.stream()
    		    .map(n -> {
    		        return NotificationDTO.builder()
    		            .content(n.getContent())
    		            .createdAt(n.getCreatedAt())
    		            .userId(n.getUser().getUserId())
    		            .type(n.getType())
    		            .targetId(n.getTargetId())
    		            .build();
    		    })
    		    .collect(Collectors.toList());
    }
}

