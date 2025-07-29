package com.example.demo.StudyGroupMember.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.StudyGroupMember.entity.StudyGroupMember;
import com.example.demo.StudyGroupMember.repository.StudyGroupMemberRepository;
import com.example.demo.notification.service.NotificationService;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.studygroup.repository.StudyGroupRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class StudyGroupServiceImpl implements StudyGroupService{
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private StudyGroupRepository studyGroupRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StudyGroupMemberRepository studyGroupMemberRepository;

	@Transactional
	public boolean requestJoinGroup(int groupId, long userId) {
	    // 1. 그룹과 사용자 조회
	    StudyGroup group = studyGroupRepository.findById(groupId)
	        .orElseThrow(() -> new RuntimeException("스터디 그룹이 존재하지 않습니다."));
	    User user = userRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("신청자 정보 없음"));

	    // 2. 중복 신청 방지 확인 로직 필요 시 여기에

	    // 3. 신청 엔티티 저장
	    StudyGroupMember member = StudyGroupMember.builder()
	        .group(group)
	        .user(user)
	        .joinStatus(StudyGroupMember.status.승인대기중)
	        .build();
	    studyGroupMemberRepository.save(member);

	    // 4. 알림 전송
	    User creator = group.getCreatedBy();  // ← 스터디 생성자
	    String content = "📢 " + user.getNickname() + "님이 '" + group.getGroupId() + "'번 스터디에 가입 신청했습니다.";

	    notificationService.sendNotification(
	        creator.getUserId(),  // 생성자에게 전송
	        content,
	        "studyJoin",
	        (long) groupId
	    );

	    return true;
	}

	


	

	
}
