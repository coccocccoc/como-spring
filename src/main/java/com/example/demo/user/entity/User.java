package com.example.demo.user.entity;


import java.util.ArrayList;
import java.util.List;

import com.example.demo.message.entity.Message;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   long userId; // 유저 ID
   
   @Column(nullable = false, length = 100)
   String socialId; // 소셜 ID
    
   @Column(nullable = true, length = 20)
   String nickname; // 닉네임
   
   @Column(nullable = true, length = 50)
   String region; // 지역
   
   @Column(nullable = true)
   int age; // 나이
   
//   @ElementCollection(fetch = FetchType.LAZY)
//    @CollectionTable(
//        name = "user_language",
//        joinColumns = @JoinColumn(name = "user_id")
//    )
//    @Column(name = "language", length = 30)
//    private List<String> languages = new ArrayList<>(); // 관심 언어
   
   @Column(nullable = false, length = 20)
   String role; // 권한
   
   public static enum provider{
      KAKAO, GOOGLE, NAVER
   }
   
   @Enumerated(EnumType.STRING)
   @Column(nullable = false, length = 20)
    Provider socialProvider;
   
   //보낸 쪽지 목록
   @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> sentMessages = new ArrayList<>();

   // ✅ 받은 쪽지 목록
   @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Message> receivedMessages = new ArrayList<>();

}
