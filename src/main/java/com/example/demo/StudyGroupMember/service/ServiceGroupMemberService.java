package com.example.demo.StudyGroupMember.service;

import java.util.List;

import com.example.demo.StudyGroupMember.dto.StudyGroupMemberDTO;
import com.example.demo.StudyGroupMember.entity.StudyGroupMember;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.user.entity.User;

public interface ServiceGroupMemberService {
	
	void applyToStudyGroup(StudyGroupMemberDTO dto);
	
	List<StudyGroupMemberDTO> getPendingMembers(int groupId);
	
	// Entity → DTO 변환
    default StudyGroupMemberDTO toDTO(StudyGroupMember entity) {
        if (entity == null) return null;

        return StudyGroupMemberDTO.builder()
                .groupId(entity.getGroup().getId())
                .userId(entity.getUser().getUserId())
                .applyTitle(entity.getApplyTitle())
                .applyContent(entity.getApplyContent())
                .build();
    }

    // DTO → Entity 변환
    default StudyGroupMember toEntity(StudyGroupMemberDTO dto, StudyGroup group, User user) {
        if (dto == null || group == null || user == null) return null;

        return StudyGroupMember.builder()
                .group(group)
                .user(user)
                .joinStatus(StudyGroupMember.status.승인대기중)
                .applyTitle(dto.getApplyTitle())
                .applyContent(dto.getApplyContent())
                .build();
    }

}
