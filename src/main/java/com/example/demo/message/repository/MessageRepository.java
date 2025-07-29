package com.example.demo.message.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.message.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByReceiver_UserId(Long receiverId);


    List<Message> findBySender_UserId(Long senderId);
    List<Message> findByReceiver_Nickname(String receiverNickname);
    List<Message> findBySender_Nickname(String senderNickname);

}
