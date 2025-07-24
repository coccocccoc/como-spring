package com.example.demo.user.dto;

import java.util.List;

import com.example.demo.message.entity.Message;
import com.example.demo.user.entity.Provider;

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
public class UserDTO {

	Long userId;
	String socialId;
	String nickname;
	String region;
	Integer age;
	String role;
	Provider socialProvider;
	List<Message> sentMessages;
	List<Message> receivedMessages;
}
