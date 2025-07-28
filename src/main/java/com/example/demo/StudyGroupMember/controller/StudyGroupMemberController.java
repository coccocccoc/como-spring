package com.example.demo.StudyGroupMember.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.StudyGroupMember.dto.StudyGroupMemberDTO;
import com.example.demo.StudyGroupMember.service.StudyGroupMemberService;
import com.example.demo.login.util.JwtTokenProvider;
import com.example.demo.studygroup.dto.StudyGroupDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

// 가입 신청 관련 컨트롤러

@RestController
@RequestMapping("/api/studies")
@RequiredArgsConstructor
public class StudyGroupMemberController {

    @Autowired
    StudyGroupMemberService memberService;
    
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/apply")
    public String applyToStudyGroup(@RequestBody StudyGroupMemberDTO dto) {
        memberService.applyToStudyGroup(dto);
        return "신청이 완료되었습니다.";
    }
    
    @GetMapping("/my")
    public List<StudyGroupDTO> getMyStudies(HttpServletRequest request) {
        // 1. Authorization 헤더에서 토큰 추출
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("JWT 토큰이 없습니다.");
        }

        // 2. "Bearer " 제거하고 실제 토큰 추출
        token = token.substring(7);

        // 3. 토큰에서 userId 추출
        Long userId = Long.parseLong(jwtTokenProvider.getUserIdFromToken(token));

        // 4. 서비스 호출
        return memberService.getMyJoinedStudies(userId);
    }
    
    // 가입 되어있는지 확인
    @GetMapping("/is-joined")
    public ResponseEntity<Boolean> checkIfUserJoined(
            @RequestParam("groupId") int groupId,
            @RequestParam("userId") Long userId) {

        boolean isJoined = memberService.isUserJoinedGroup(groupId, userId);
        return ResponseEntity.ok(isJoined);
    }
    
    // 가입한 인원 수 체크
    @GetMapping("/members/count")
    public ResponseEntity<Integer> countApprovedMembers(@RequestParam("groupId") int groupId) {
        int count = memberService.countApprovedMembers(groupId);
        return ResponseEntity.ok(count);
    }



}
