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
	
	int memberId; // ✅ 추가: DB에서 식별자
	int groupId;
    long userId;
    String nickname;
    String applyTitle;
    String applyContent;
    StudyGroupMember.status joinStatus;

    
    public static StudyGroupMemberDTO fromEntity(StudyGroupMember member) {
        return StudyGroupMemberDTO.builder()
            .memberId(member.getId()) // ✅ 추가
            .groupId(member.getGroup().getId())
            .userId(member.getUser().getUserId())
            .nickname(member.getUser().getNickname())
            .applyTitle(member.getApplyTitle())
            .applyContent(member.getApplyContent())
            .joinStatus(member.getJoinStatus())
            .build();
    }



}
