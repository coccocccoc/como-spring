package com.example.demo.StudyGroupMember.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.StudyGroupMember.dto.StudyGroupMemberDTO;
import com.example.demo.StudyGroupMember.service.ServiceGroupMemberService;

@RestController
@RequestMapping("/api/studies")
public class StudyGroupMemberController {

    @Autowired
    ServiceGroupMemberService memberService;

    @PostMapping("/apply")
    public String applyToStudyGroup(@RequestBody StudyGroupMemberDTO dto) {
        memberService.applyToStudyGroup(dto);
        return "신청이 완료되었습니다.";
    }

}
