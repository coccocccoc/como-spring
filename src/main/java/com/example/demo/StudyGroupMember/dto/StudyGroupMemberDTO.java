package com.example.demo.StudyGroupMember.dto;

import com.example.demo.StudyGroupMember.entity.StudyGroupMember;

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
    StudyGroupMember.status joinStatus;

    
    public StudyGroupMemberDTO fromEntity(StudyGroupMember member) {
        StudyGroupMemberDTO dto = new StudyGroupMemberDTO();
        dto.setGroupId(member.getGroup().getId());
        dto.setUserId(member.getUser().getUserId());
        dto.setNickname(member.getUser().getNickname());
        dto.setApplyTitle(member.getApplyTitle());
        dto.setApplyContent(member.getApplyContent());
        dto.setJoinStatus(member.getJoinStatus());
        return dto;
    }



}
