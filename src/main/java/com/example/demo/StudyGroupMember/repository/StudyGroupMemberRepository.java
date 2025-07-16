package com.example.demo.StudyGroupMember.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.StudyGroupMember.entity.StudyGroupMember;

public interface StudyGroupMemberRepository extends JpaRepository<StudyGroupMember, Integer> {
	// 해당 유저가 그 그룹에 가입된 상태인지 확인
	boolean existsByGroup_GroupIdAndUser_UserId(int groupId, int userId);
}
