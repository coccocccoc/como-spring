package com.example.demo.comment.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentDTO {
	
	int commentId;
	
	int groupPostId;
	
	long userId;
	
	String nickname;
	
	String content;
	
	LocalDateTime regDate;
	
}
