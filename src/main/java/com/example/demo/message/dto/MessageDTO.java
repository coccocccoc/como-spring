package com.example.demo.message.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
	Long id; 
     Long senderId;
     Long receiverId;
     String title;
     String senderNickname; // 알림에 사용
     String receiverNickname;
     String content;
     LocalDateTime sentAt;
}

