package com.example.demo.StudyGroupMember.dto;

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
public class StudyGroupMemberDTO {
	
	int groupId;
    long userId;
    String nickname;
    String applyTitle;
    String applyContent;

}
