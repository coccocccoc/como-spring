// MessageService.java
package com.example.demo.message.service;

import java.util.List;

import com.example.demo.message.dto.MessageDTO;
import com.example.demo.message.entity.Message;

public interface MessageService {
	
    boolean sendMessage(MessageDTO dto);
    
    List<MessageDTO> getReceivedMessages(Long receiverId); // 받은 쪽지
    List<MessageDTO> getSentMessages(Long senderId);       // 보낸 쪽지
    

    default MessageDTO messageToDto(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .senderId(message.getSender().getUserId())
                .receiverId(message.getReceiver().getUserId())
                .senderNickname(message.getSender().getNickname())
                .receiverNickname(message.getReceiver().getNickname())
                .title(message.getTitle())
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .build();
    }
}
