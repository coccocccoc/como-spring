package com.example.demo.user.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.message.entity.Message;
import com.example.demo.user.entity.User.provider;

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
	String email;
	String role;
	provider socialProvider;
	MultipartFile uploadFile; // 파일 스트림

	String imgPath;
}
