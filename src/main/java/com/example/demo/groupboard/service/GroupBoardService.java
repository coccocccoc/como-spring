package com.example.demo.groupboard.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.groupboard.dto.GroupBoardDTO;
import com.example.demo.groupboard.entity.GroupBoard;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.user.entity.User;

public interface GroupBoardService {

	// 게시물 등록
	int registerGroupPost(GroupBoardDTO dto);
	
	// 게시물 목록 조회 (페이징)
	Page<GroupBoardDTO> getGroupPostsList(int groupId, Pageable pageable);
	
	// 게시물 상세 조회
	GroupBoardDTO findGroupPostById(int postId);

	// 게시물 수정
	void updateGroupPost(int postId, GroupBoardDTO dto);
	
	// 게시물 삭제
	void deleteGroupPost(int postId, Integer requesterId);
	
	// 유저가 해당 그룹에 가입했는지 확인
	void validateUserMembership(int groupId, int userId);
	
	// DTO → Entity
	default GroupBoard toGroupBoardEntity(GroupBoardDTO dto) {
	    return GroupBoard.builder()
	            .groupId(StudyGroup.builder().groupId(dto.getGroupId()).build())
	            .userId(User.builder().userId(dto.getUserId()).build())
	            .category(GroupBoard.cat.valueOf(dto.getCategory()))
	            .title(dto.getTitle())
	            .content(dto.getContent())
	            .regDate(dto.getRegDate()) // 선택 사항
	            .build();
	}

	// Entity → DTO
	default GroupBoardDTO toGroupBoardDTO(GroupBoard entity) {
	    return GroupBoardDTO.builder()
	            .groupId(entity.getGroupId().getGroupId())
	            .userId(entity.getUserId().getUserId())
	            .category(entity.getCategory().name())
	            .title(entity.getTitle())
	            .content(entity.getContent())
	            .regDate(entity.getRegDate())
	            .build();
	}
	
}
