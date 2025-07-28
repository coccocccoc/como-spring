package com.example.demo.recruitboard.service;

import java.nio.file.AccessDeniedException;
import java.util.List;

import com.example.demo.recruitboard.dto.RecruitBoardDTO;
import com.example.demo.recruitboard.entity.RecruitBoard;
import com.example.demo.techstack.entity.TechStack;
import com.example.demo.user.entity.User;

public interface RecruitBoardService {
	
	// 모집글 등록
    RecruitBoardDTO createRecruitBoard(RecruitBoardDTO dto);

    // 모집글 전체 조회
    List<RecruitBoardDTO> getAllRecruitBoards();

    // 모집글 상세 조회
    RecruitBoardDTO getRecruitBoardById(int recruitPostId);

    // 모집글 수정
    RecruitBoardDTO updateRecruitBoard(int recruitPostId, RecruitBoardDTO dto) throws AccessDeniedException;

    // 모집글 삭제
    void deleteRecruitBoard(int recruitPostId, int requesterId) throws AccessDeniedException;
    
    // 서비스 인터페이스
    RecruitBoardDTO getRecruitBoardByGroupId(int groupId);

    
    default RecruitBoardDTO toRecruitBoardDTO(RecruitBoard entity) {
        return RecruitBoardDTO.builder()
                .recruitPostId(entity.getRecruitPostId())
                .groupId(entity.getStudyGroup().getId())
                .userId(entity.getWriter().getUserId())
                .nickname(entity.getWriter().getNickname())
                .title(entity.getTitle())
                .content(entity.getContent())
                .regDate(entity.getRegDate())
                .capacity(entity.getCapacity())
                .mode(entity.getMode())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .deadline(entity.getDeadline())
                .techStackIds(entity.getTechStacks().stream()
                        .map(TechStack::getTechStackId)
                        .toList())
                .techStackNames(entity.getTechStacks().stream()
                        .map(TechStack::getName)
                        .toList())
                .approvedMemberCount(0) // 임시 값. 실제 값은 구현체에서 세팅 필요
                .build();
    }


    default RecruitBoard toRecruitBoardEntity(RecruitBoardDTO dto) {
        return RecruitBoard.builder()
                .recruitPostId(dto.getRecruitPostId())
                .writer(User.builder().userId(dto.getUserId()).build())
                .title(dto.getTitle())
                .content(dto.getContent())
                .capacity(dto.getCapacity())
                .mode(dto.getMode())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .deadline(dto.getDeadline())
                // techStacks는 ServiceImpl에서 따로 설정
                .build();
    }
}
