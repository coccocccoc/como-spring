package com.example.demo.studygroup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.util.JwtTokenProvider;
import com.example.demo.studygroup.dto.StudyGroupDTO;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.studygroup.repository.StudyGroupRepository;
import com.example.demo.user.entity.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/study-groups")
public class StudyGroupController {

	@Autowired
	StudyGroupRepository groupRepo;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@GetMapping("/{id}")
	public ResponseEntity<?> getGroupInfo(@PathVariable("id") int groupId, HttpServletRequest request) {
		StudyGroup group = groupRepo.findById(groupId)
			.orElseThrow(() -> new RuntimeException("해당 스터디 그룹이 존재하지 않습니다."));

		// 👇 필요한 정보만 추려서 DTO 또는 JSON으로 반환
		User leader = group.getCreatedBy();

		// 클라이언트가 스터디장 여부 비교할 수 있게 creatorId를 포함해서 반환
		return ResponseEntity.ok(
				new StudyGroupDTO(group.getId(), group.getRecruitBoard().getTitle(), leader.getUserId())
	        );
	    }

}
