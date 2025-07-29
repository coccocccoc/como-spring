package com.example.demo.recruitboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.recruitboard.entity.RecruitBoard;

public interface RecruitBoardRepository extends JpaRepository<RecruitBoard, Integer> {

	Optional<RecruitBoard> findByStudyGroup_Id(int groupId);
	
	List<RecruitBoard> findByWriter_UserId(Long userId);

}
