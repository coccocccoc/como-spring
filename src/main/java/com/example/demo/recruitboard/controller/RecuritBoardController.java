package com.example.demo.recruitboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.login.util.JwtTokenProvider;
import com.example.demo.recruitboard.dto.RecruitBoardDTO;
import com.example.demo.recruitboard.service.RecruitBoardService;


@RestController
@RequestMapping("/api/studies")
public class RecuritBoardController {

	@Autowired
	RecruitBoardService recruitBoardService;
	
	@Autowired
	JwtTokenProvider jwtTokenProvider;

	// 1. 모집글 등록
	@PostMapping
	public ResponseEntity<RecruitBoardDTO> createRecruit(@RequestBody RecruitBoardDTO dto) {
		RecruitBoardDTO created = recruitBoardService.createRecruitBoard(dto);
		return ResponseEntity.ok(created);
	}

	// 2. 모집글 전체 조회
	@GetMapping
	public ResponseEntity<List<RecruitBoardDTO>> getAllRecruits() {
		List<RecruitBoardDTO> list = recruitBoardService.getAllRecruitBoards();
		return ResponseEntity.ok(list);
	}

	// 3. 모집글 상세 조회
	@GetMapping("/{id}")
	public ResponseEntity<RecruitBoardDTO> getRecruitById(@PathVariable("id") int id) {
		RecruitBoardDTO dto = recruitBoardService.getRecruitBoardById(id);
		return ResponseEntity.ok(dto);
	}

	// 4. 모집글 수정
	@PutMapping("/{id}")
	public ResponseEntity<RecruitBoardDTO> updateRecruit(
			@PathVariable("id") int id,
			@RequestBody RecruitBoardDTO dto) throws Exception {
		RecruitBoardDTO updated = recruitBoardService.updateRecruitBoard(id, dto);
		return ResponseEntity.ok(updated);
	}

	// 5. 모집글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRecruit(
			@PathVariable("id") int id,
			@RequestParam("userId") int userId) throws Exception {
		recruitBoardService.deleteRecruitBoard(id, userId);
		return ResponseEntity.noContent().build();
	}
	
	
	// 스터디 그룹 ID로 모집글 조회
	@GetMapping("/group/{groupId}")
	public ResponseEntity<RecruitBoardDTO> getRecruitByGroupId(@PathVariable("groupId") int groupId) {
	    RecruitBoardDTO dto = recruitBoardService.getRecruitBoardByGroupId(groupId);
	    return ResponseEntity.ok(dto);
	}
	
	
	@GetMapping("/my-created")
	public ResponseEntity<List<RecruitBoardDTO>> getMyStudies(@RequestHeader("Authorization") String token) {
	    Long userId = jwtTokenProvider.extractUserId(token);
	    return ResponseEntity.ok(recruitBoardService.getMyCreatedStudies(userId));
	}


	
	
}

