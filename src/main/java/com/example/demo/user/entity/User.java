package com.example.demo.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.message.entity.Message;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "user",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"socialId", "socialProvider"})  // 소셜 ID + 제공자 중복 방지
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(nullable = false, length = 100)
    private String socialId;

    @Column(nullable = true, length = 20)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String role;

    public enum provider {
        KAKAO, GOOGLE, NAVER
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private provider socialProvider;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> sentMessages = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> receivedMessages = new ArrayList<>();
}
