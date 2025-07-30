package com.example.demo.StudyGroupMember.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.StudyGroupMember.entity.StudyGroupMember;

public interface StudyGroupMemberRepository extends JpaRepository<StudyGroupMember, Integer> {
	
	// 해당 유저가 그 그룹에 가입된 상태인지 확인
	boolean existsByGroup_IdAndUser_UserId(int groupId, Long userId);
	
	// 승인 대기 회원만 조회
	List<StudyGroupMember> findByGroup_IdAndJoinStatus(int groupId, StudyGroupMember.status status);
	
	Optional<StudyGroupMember> findByGroup_IdAndUser_UserId(int groupId, long userId);
	
	// 스터디 상태가 모집중 또는 활동중이고, 가입 상태가 가입인 스터디 조회
	List<StudyGroupMember> findByUser_UserIdAndJoinStatus(long userId, StudyGroupMember.status status);

	// 해당 스터디 그룹의 상태가 가입인 회원 수 조회 
	int countByGroup_IdAndJoinStatus(int groupId, StudyGroupMember.status status);

	List<StudyGroupMember> findByUser_UserIdAndJoinStatus(Long userId, StudyGroupMember.status joinStatus);
	
	// ✅ 특정 그룹의 모든 멤버 조회 (삭제할 때 필요)
    List<StudyGroupMember> findByGroup_Id(int groupId);

//    Optional<StudyGroupMember> findByGroup_IdAndUser_Id(int groupId, long userId);

}
