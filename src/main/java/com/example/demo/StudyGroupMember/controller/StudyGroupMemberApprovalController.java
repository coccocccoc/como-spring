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

// 가입 승인 관련 컨트롤러

@RestController
@RequestMapping("/api/study-group-members")
public class StudyGroupMemberApprovalController {

    @Autowired
    private StudyGroupMemberService memberService;

    // 대기 목록 조회
    @GetMapping("/pending")
    public ResponseEntity<List<StudyGroupMemberDTO>> getPendingMembers(@RequestParam("groupId") int groupId) {
        System.out.println("🔍 groupId 확인: " + groupId);
        List<StudyGroupMemberDTO> pending = memberService.getPendingMembersByGroupId(groupId);
        return ResponseEntity.ok(pending);
    }

    // 승인
    @PostMapping("/approve/{memberId}")
    public ResponseEntity<Void> approveMember(@PathVariable int memberId) {
        memberService.approveMember(memberId);
        return ResponseEntity.ok().build();
    }

    // 거절
    @PostMapping("/reject/{memberId}")
    public ResponseEntity<Void> rejectMember(@PathVariable int memberId) {
        memberService.rejectMember(memberId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/application")
    public ResponseEntity<StudyGroupMemberDTO> getApplication(
            @RequestParam("groupId") int groupId,
            @RequestParam("userId") int userId) {
        
    	System.out.println("🔥 groupId: " + groupId + ", userId: " + userId);
        return ResponseEntity.ok(memberService.getApplication(groupId, userId));
    }

    
}
