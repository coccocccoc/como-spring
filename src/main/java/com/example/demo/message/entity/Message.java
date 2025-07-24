package com.example.demo.message.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.user.entity.User;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id; // 메시지 고유 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
     User sender; // 보낸 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
     User receiver; // 받는 사람

    @Column(nullable = false, length = 100)
     String title; // 메시지 제목

    @Column(nullable = false, columnDefinition = "TEXT")
     String content; // 메시지 내용

    @Column(nullable = false)
    @CreatedDate
     LocalDateTime sentAt; // 발송 시간

    @PrePersist
     void onCreate() {
        this.sentAt = LocalDateTime.now();
    }
}

//@Id

