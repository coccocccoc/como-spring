package com.example.demo.groupboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.StudyGroupMember.repository.StudyGroupMemberRepository;
import com.example.demo.groupboard.dto.GroupBoardDTO;
import com.example.demo.groupboard.entity.GroupBoard;
import com.example.demo.groupboard.entity.GroupBoard.cat;
import com.example.demo.groupboard.repository.GroupBoardRepository;
import com.example.demo.studygroup.repository.StudyGroupRepository;
import com.example.demo.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class GroupBoardServiceImpl implements GroupBoardService {
	
	@Autowired
	GroupBoardRepository groupBoardRepo;
	
	@Autowired
	StudyGroupRepository groupRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	StudyGroupMemberRepository studyGroupMemberRepo;

    @Override
    public int registerGroupPost(GroupBoardDTO dto) {
        int groupId = dto.getGroupId();
        int userId = dto.getUserId();

        validateUserMembership(groupId, userId);

        GroupBoard entity = toGroupBoardEntity(dto);
        return groupBoardRepo.save(entity).getGroupPostId();
    }

	@Override
    public Page<GroupBoardDTO> getGroupPostsList(int groupId, Pageable pageable) {
        // 임시 유저 ID로 대체
        int tempUserId = 1; // 실제 로그인 정보 적용 필요
        validateUserMembership(groupId, tempUserId);

        return groupBoardRepo.findByGroupId_GroupId(groupId, pageable)
                             .map(this::toGroupBoardDTO);
    }

	@Override
	public GroupBoardDTO findGroupPostById(int postId) {
		GroupBoard post = groupBoardRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));
		return toGroupBoardDTO(post);
	}

	@Transactional
	@Override
	public void updateGroupPost(int postId, GroupBoardDTO dto) {
	    GroupBoard post = groupBoardRepo.findById(postId)
	            .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));

	    post.setTitle(dto.getTitle());
	    post.setContent(dto.getContent());

	    // 문자열 → enum 변환 (예: "자유방")
	    post.setCategory(cat.valueOf(dto.getCategory()));

	    groupBoardRepo.save(post); // 명시적으로 저장
	}

    @Override
    public void deleteGroupPost(int postId, Integer requesterId) {
        GroupBoard post = groupBoardRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));

        if (post.getUserId().getUserId() != requesterId) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }

        groupBoardRepo.delete(post);
    }
	
	@Override
	public void validateUserMembership(int groupId, int userId) {
//	    boolean isMember = studyGroupMemberRepo.existsByGroup_GroupIdAndUser_UserId(groupId, userId);
//	    if (!isMember) {
//	        throw new IllegalStateException("이 사용자는 해당 그룹에 가입되어 있지 않습니다.");
//	    }
	}

}
