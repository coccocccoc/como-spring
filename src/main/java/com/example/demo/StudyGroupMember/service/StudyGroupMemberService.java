package com.example.demo.StudyGroupMember.service;

import java.util.List;

import com.example.demo.StudyGroupMember.dto.StudyGroupMemberDTO;
import com.example.demo.StudyGroupMember.entity.StudyGroupMember;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.user.entity.User;
import com.example.demo.studygroup.dto.StudyGroupDTO;

public interface StudyGroupMemberService {
	
	void applyToStudyGroup(StudyGroupMemberDTO dto);
		
    List<StudyGroupMemberDTO> getPendingMembersByGroupId(int groupId);
    
    void approveMember(int memberId);
    
    void rejectMember(int memberId);
    
    public StudyGroupMemberDTO getApplication(int groupId, int userId);
    
    public List<StudyGroupDTO> getMyJoinedStudies(Long userId);
    
    boolean isUserJoinedGroup(int groupId, Long userId);
    
    int countApprovedMembers(int groupId);
    
    List<StudyGroupDTO> getMyEndedStudies(Long userId);
	
	// Entity → DTO 변환
    default StudyGroupMemberDTO toDTO(StudyGroupMember entity) {
        if (entity == null) return null;

        return StudyGroupMemberDTO.builder()
                .groupId(entity.getGroup().getId())
                .userId(entity.getUser().getUserId())
                .nickname(entity.getUser().getNickname())
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
