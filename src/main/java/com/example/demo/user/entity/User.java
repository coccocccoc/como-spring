package com.example.demo.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.message.entity.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = { "socialId", "socialProvider" }) // 소셜 ID +
																												// 제공자
																												// 중복 방지
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long userId;

	@Column(nullable = false, length = 100)
	String socialId;

	@Column(nullable = true, length = 20)
	String nickname;
	
	@Column(nullable = true, length = 20)
	String email;

	@Column(nullable = false, length = 20)
	String role;

	public enum provider {
		KAKAO, GOOGLE, NAVER
	}

	 @Column(length = 200, nullable = true)
	String imgPath;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	provider socialProvider;

	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	@ToString.Exclude
	@Builder.Default
	 List<Message> sentMessages = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	@ToString.Exclude
	@Builder.Default
	 List<Message> receivedMessages = new ArrayList<>();


}
