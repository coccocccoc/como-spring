// MessageServiceImpl.java
package com.example.demo.message.service;

import com.example.demo.message.dto.MessageDTO;
import com.example.demo.message.entity.Message;
import com.example.demo.message.repository.MessageRepository;
import com.example.demo.notification.entity.Notification;
import com.example.demo.notification.repository.NotificationRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

	@Autowired
    MessageRepository messageRepository;
	
	@Autowired
    UserRepository userRepository;
	
	@Autowired
    SimpMessagingTemplate messagingTemplate;
	
	@Autowired
    NotificationRepository notificationRepository;

    @Override
    public boolean sendMessage(MessageDTO dto) {
        try {
            User sender = userRepository.findById(dto.getSenderId())
                    .orElseThrow(() -> new RuntimeException("보내는 사용자 없음"));
            User receiver = userRepository.findById(dto.getReceiverId())
                    .orElseThrow(() -> new RuntimeException("받는 사용자 없음"));

            Message message = messageRepository.save(Message.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .build());

            Long messageId = message.getId();

            // WebSocket 알림
            String notify = "✉️ " + sender.getNickname() + "님에게 쪽지가 도착했습니다.";
            
            Notification notification = Notification.builder()
                    .user(receiver)             // 알림 수신자
                    .content(notify)            // 알림 내용
                    .type("message")            // 알림 유형
                    .targetId(messageId)
                    .build();
            notificationRepository.save(notification);
            
            System.out.println("쪽지 전송 완료. WebSocket 알림 전송 시작.");
            messagingTemplate.convertAndSend("/topic/notifications/" + receiver.getUserId(), notify);


            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	@Transactional
    public List<MessageDTO> getReceivedMessages(Long receiverId) {
		return messageRepository.findByReceiver_UserId(receiverId).stream()
	            .map(this::messageToDto)
	            .collect(Collectors.toList());
    }

	@Override
	 @Transactional
	    public List<MessageDTO> getSentMessages(Long senderId) {
	        return messageRepository.findBySender_UserId(senderId).stream()
	                .map(this::messageToDto)
	                .collect(Collectors.toList());
	    }
	

}
