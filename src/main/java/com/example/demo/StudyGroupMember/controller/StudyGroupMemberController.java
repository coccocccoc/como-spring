package com.example.demo.StudyGroupMember.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.StudyGroupMember.dto.StudyGroupMemberDTO;
import com.example.demo.StudyGroupMember.service.StudyGroupMemberService;
import com.example.demo.login.util.JwtTokenProvider;
import com.example.demo.studygroup.dto.StudyGroupDTO;
import com.example.demo.user.entity.User;

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
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("JWT 토큰이 없습니다.");
        }
        token = token.substring(7);
        Long userId = Long.parseLong(jwtTokenProvider.getUserIdFromToken(token));
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
    
    // 종료된 스터디
    @GetMapping("/my-ended")
    public List<StudyGroupDTO> getMyEndedStudies(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);

        String userIdStr = jwtTokenProvider.getUserIdFromToken(token);

        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "userId 형식이 잘못되었습니다: " + userIdStr);
        }

        return memberService.getMyEndedStudies(userId);
    }









}
