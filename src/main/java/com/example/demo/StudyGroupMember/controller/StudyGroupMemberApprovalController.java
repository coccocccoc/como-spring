package com.example.demo.StudyGroupMember.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.StudyGroupMember.dto.StudyGroupMemberDTO;
import com.example.demo.StudyGroupMember.service.StudyGroupMemberService;
import com.example.demo.login.util.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;

// 가입 승인 관련 컨트롤러

@RestController
@RequestMapping("/api/study-group-members")
public class StudyGroupMemberApprovalController {

    @Autowired
    private StudyGroupMemberService memberService;
    
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @GetMapping("/application")
    public ResponseEntity<List<StudyGroupMemberDTO>> getApplicants(@RequestParam("groupId") int groupId) {
        return getPendingMembers(groupId); // 기존 메서드 재사용
    }

    // 대기 목록 조회
    @GetMapping("/pending")
    public ResponseEntity<List<StudyGroupMemberDTO>> getPendingMembers(@RequestParam("groupId") int groupId) {
        System.out.println("🔍 groupId 확인: " + groupId);
        List<StudyGroupMemberDTO> pending = memberService.getPendingMembersByGroupId(groupId);
        return ResponseEntity.ok(pending);
    }

    // 승인
    @PostMapping("/approve")
    public ResponseEntity<Void> approveMember(
            @RequestParam("userId") Long userId,
            @RequestParam("groupId") int groupId,
            HttpServletRequest request) {

        Long requesterId = jwtTokenProvider.getUserIdFromToken(request);

        if (!memberService.isGroupLeader(groupId, requesterId)) {
            return ResponseEntity.status(403).build();
        }

        memberService.approveMember(userId, groupId);
        return ResponseEntity.ok().build();
    }

    // 거절
    @PostMapping("/reject")
    public ResponseEntity<Void> rejectMember(
            @RequestParam("userId") Long userId,
            @RequestParam("groupId") int groupId,
            HttpServletRequest request) {

        Long requesterId = jwtTokenProvider.getUserIdFromToken(request);

        if (!memberService.isGroupLeader(groupId, requesterId)) {
            return ResponseEntity.status(403).build();
        }

        memberService.rejectMember(userId, groupId);
        return ResponseEntity.ok().build();
    }

    
}
