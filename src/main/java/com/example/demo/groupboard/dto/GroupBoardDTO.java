package com.example.demo.groupboard.dto;

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
public class GroupBoardDTO {
	
	int groupPostId;
		
	int groupId; 
	
	long userId;
	
	String category;

	String title;
	
	String content;
	
	LocalDateTime regDate;
	
	String nickname;
}
