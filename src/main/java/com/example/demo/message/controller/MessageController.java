package com.example.demo.message.controller;

import com.example.demo.message.dto.MessageDTO;
import com.example.demo.message.service.MessageService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "http://localhost:3000")
public class MessageController {

	@Autowired
  MessageService messageService;


    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO dto) {
        boolean result = messageService.sendMessage(dto);

        if (result) {
            return ResponseEntity.ok("쪽지 전송 완료");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("쪽지 전송 실패");
        }
    }
    
    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<MessageDTO>> getReceivedMessages(@PathVariable("receiverId") Long receiverId) {
        List<MessageDTO> messages = messageService.getReceivedMessages(receiverId);
        return ResponseEntity.ok(messages);
    }
    
    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<MessageDTO>> getSentMessages(@PathVariable("senderId") Long senderId) {
        List<MessageDTO> messages = messageService.getSentMessages(senderId);
        return ResponseEntity.ok(messages);
    }

}
