package com.example.demo.StudyGroupMember.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.StudyGroupMember.entity.StudyGroupMember;

public interface StudyGroupMemberRepository extends JpaRepository<StudyGroupMember, Integer> {
	
	// 해당 유저가 그 그룹에 가입된 상태인지 확인
	boolean existsByGroup_IdAndUser_UserId(int groupId, int userId);
	
	// 승인 대기 회원만 조회
	List<StudyGroupMember> findByGroup_GroupIdAndJoinStatus(int groupId, StudyGroupMember.status status);

	
}
