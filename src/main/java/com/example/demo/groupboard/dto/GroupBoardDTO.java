package com.example.demo.groupboard.dto;

import java.time.LocalDateTime;

import com.example.demo.groupboard.entity.GroupBoard;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.user.entity.User;

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
		
	int groupId;
	
	int userId;
	
	String category;

	String title;
	
	String content;
	
	LocalDateTime regDate;
}
