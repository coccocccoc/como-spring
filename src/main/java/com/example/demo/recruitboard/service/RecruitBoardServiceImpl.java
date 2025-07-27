package com.example.demo.recruitboard.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.StudyGroupMember.entity.StudyGroupMember;
import com.example.demo.StudyGroupMember.repository.StudyGroupMemberRepository;
import com.example.demo.recruitboard.dto.RecruitBoardDTO;
import com.example.demo.recruitboard.entity.RecruitBoard;
import com.example.demo.recruitboard.repository.RecruitBoardRepository;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.studygroup.service.StudyGroupService;
import com.example.demo.techstack.entity.TechStack;
import com.example.demo.techstack.repository.TechStackRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RecruitBoardServiceImpl implements RecruitBoardService {
	
	@Autowired
	RecruitBoardRepository recruitBoardRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	TechStackRepository techStackRepo;
	
	@Autowired
	StudyGroupService studyGroupService;
	
	@Autowired
	StudyGroupMemberRepository studyGroupMemberRepo;
	
	// 모집글 등록
	@Override
    @Transactional
    public RecruitBoardDTO createRecruitBoard(RecruitBoardDTO dto) {
	    // 1. 작성자 유저 가져오기
	    User writer = userRepo.findById(dto.getUserId())
	            .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

	    // 2. 기술스택 가져오기
	    List<TechStack> techStacks = techStackRepo.findAllById(dto.getTechStackIds());

	    // 3. DTO -> Entity
	    RecruitBoard board = toRecruitBoardEntity(dto);
	    board.setWriter(writer);
	    board.setTechStacks(techStacks);

	    // 4. 스터디 그룹 생성
	    StudyGroup studyGroup = studyGroupService.createFromRecruitBoard(dto);
	    board.setStudyGroup(studyGroup); // 양방향 연결

	    // 5. 작성자를 StudyGroupMember로 등록
	    StudyGroupMember member = new StudyGroupMember();
	    member.setGroup(studyGroup);
	    member.setUser(writer);
	    member.setJoinStatus(StudyGroupMember.status.가입);
	    studyGroupMemberRepo.save(member);

	    // 6. 모집글 저장
	    RecruitBoard saved = recruitBoardRepo.save(board);
	    return toRecruitBoardDTO(saved);
    }

	// 모집글 전체 조회
	@Override
    public List<RecruitBoardDTO> getAllRecruitBoards() {
        return recruitBoardRepo.findAll().stream()
                .map(this::toRecruitBoardDTO)
                .collect(Collectors.toList());
    }

	// 모집글 상세 조회
	@Override
    public RecruitBoardDTO getRecruitBoardById(int recruitPostId) {
        RecruitBoard board = recruitBoardRepo.findById(recruitPostId)
                .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));
        return toRecruitBoardDTO(board);
    }

	// 모집글 수정
	@Override
	@Transactional
	public RecruitBoardDTO updateRecruitBoard(int recruitPostId, RecruitBoardDTO dto) throws AccessDeniedException {
	    RecruitBoard board = recruitBoardRepo.findById(recruitPostId)
	            .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));

	    // 작성자 본인인지 확인
	    if (board.getWriter() == null || board.getWriter().getUserId() != dto.getUserId()) {
	        throw new AccessDeniedException("작성자만 수정할 수 있습니다.");
	    }

	    board.setTitle(dto.getTitle());
	    board.setContent(dto.getContent());
	    board.setCapacity(dto.getCapacity());
	    board.setMode(dto.getMode());
	    board.setStartDate(dto.getStartDate());
	    board.setEndDate(dto.getEndDate());
	    board.setDeadline(dto.getDeadline());

	    List<TechStack> techStacks = techStackRepo.findAllById(dto.getTechStackIds());
	    board.setTechStacks(techStacks);

	    return toRecruitBoardDTO(board);
	}

    // 모집글 삭제
	@Override
	@Transactional
	public void deleteRecruitBoard(int recruitPostId, int requesterId) throws AccessDeniedException {
	    RecruitBoard board = recruitBoardRepo.findById(recruitPostId)
	            .orElseThrow(() -> new IllegalArgumentException("해당 모집글이 존재하지 않습니다."));

	    // 작성자 본인인지 확인
	    if (board.getWriter().getUserId() != requesterId) {
	        throw new AccessDeniedException("작성자만 삭제할 수 있습니다.");
	    }

	    recruitBoardRepo.delete(board);
	}
	
	// 서비스 구현체
	@Override
	public RecruitBoardDTO getRecruitBoardByGroupId(int groupId) {
	    return recruitBoardRepo.findByStudyGroup_Id(groupId)
	        .map(this::toRecruitBoardDTO)
	        .orElseThrow(() -> new IllegalArgumentException("해당 그룹의 모집글을 찾을 수 없습니다."));
	}



}
