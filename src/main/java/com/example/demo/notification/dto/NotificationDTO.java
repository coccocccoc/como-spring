package com.example.demo.notification.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotificationDTO {

	String content;
	
	LocalDateTime createdAt;
	
	Long userId; // 알림의 주인 (user.id)
	
	 String type; // 🔥 알림 종류 ("message", "notice", "comment" 등)
	 
	 Long targetId; 

}
