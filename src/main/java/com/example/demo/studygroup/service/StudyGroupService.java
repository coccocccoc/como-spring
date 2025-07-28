package com.example.demo.studygroup.service;

import com.example.demo.recruitboard.dto.RecruitBoardDTO;
import com.example.demo.studygroup.dto.StudyGroupDTO;
import com.example.demo.studygroup.entity.StudyGroup;

public interface StudyGroupService {

	StudyGroup createFromRecruitBoard(RecruitBoardDTO dto);
	
	default StudyGroupDTO toStudyGroupDTO(StudyGroup group) {
	    if (group == null || group.getRecruitBoard() == null) return null;

	    return StudyGroupDTO.builder()
	        .groupId(group.getId())
	        .title(group.getRecruitBoard().getTitle())
	        .content(group.getRecruitBoard().getContent())
	        .nickname(group.getCreatedBy().getNickname())
	        .regDate(group.getRecruitBoard().getRegDate())
	        .capacity(group.getRecruitBoard().getCapacity())
	        .startDate(group.getRecruitBoard().getStartDate())
	        .endDate(group.getRecruitBoard().getEndDate())
	        .deadline(group.getRecruitBoard().getDeadline())
	        .mode(group.getRecruitBoard().getMode().name())
	        .techStackNames(
	            group.getRecruitBoard().getTechStacks().stream()
	                .map(tech -> tech.getName())
	                .toList()
	        )
	        .status(group.getStatus().name())
	        .build();
	}

	
}
