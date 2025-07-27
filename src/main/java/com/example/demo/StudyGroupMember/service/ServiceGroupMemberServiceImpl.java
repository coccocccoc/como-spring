package com.example.demo.StudyGroupMember.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.StudyGroupMember.dto.StudyGroupMemberDTO;
import com.example.demo.StudyGroupMember.entity.StudyGroupMember;
import com.example.demo.StudyGroupMember.entity.StudyGroupMember.status;
import com.example.demo.StudyGroupMember.repository.StudyGroupMemberRepository;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.studygroup.repository.StudyGroupRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

@Service
public class ServiceGroupMemberServiceImpl implements ServiceGroupMemberService {
	
	@Autowired
    StudyGroupMemberRepository memberRepo;

    @Autowired
    StudyGroupRepository groupRepo;

    @Autowired
    UserRepository userRepo;

	@Override
	public void applyToStudyGroup(StudyGroupMemberDTO dto) {
		
		StudyGroup group = groupRepo.findById(dto.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다."));
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        StudyGroupMember member = StudyGroupMember.builder()
                .group(group)
                .user(user)
                .joinStatus(status.승인대기중)
                .applyTitle(dto.getApplyTitle())
                .applyContent(dto.getApplyContent())
                .build();

        memberRepo.save(member);
    }

	@Override
	public List<StudyGroupMemberDTO> getPendingMembers(int groupId) {
	    List<StudyGroupMember> members = memberRepo.findByGroup_GroupIdAndJoinStatus(
	        groupId, StudyGroupMember.status.승인대기중
	    );
	    
	    return members.stream()
	            .map(this::toDTO)
	            .collect(Collectors.toList());
	}

}
