package com.example.demo.user.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.groupboard.entity.GroupBoard;
import com.example.demo.groupboard.repository.GroupBoardRepository;
import com.example.demo.recruitboard.entity.RecruitBoard;
import com.example.demo.recruitboard.repository.RecruitBoardRepository;
import com.example.demo.user.dto.MyPostDTO;

@Service
public class MyPostServiceImpl implements MyPostService {
	
	@Autowired
	RecruitBoardRepository recruitBoardRepository;
	
	@Autowired
    GroupBoardRepository groupBoardRepository;


	@Override
	public List<MyPostDTO> getAllMyPosts(Long userId) {
	    List<MyPostDTO> result = new ArrayList<>();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

	    // ✅ 모집글
	    List<RecruitBoard> recruitBoards = recruitBoardRepository.findByWriter_UserId(userId);
	    for (RecruitBoard rb : recruitBoards) {
	        result.add(MyPostDTO.builder()
	            .postId(rb.getRecruitPostId())
	            .groupId(rb.getStudyGroup().getId())
	            .title(rb.getTitle())
	            .nickname(rb.getWriter().getNickname())
	            .createdDate(rb.getRegDate().format(formatter))
	            .type("recruit")
	            .url("/studies/detail/" + rb.getRecruitPostId()) // ✅ URL 지정
	            .build());
	    }

	    // ✅ 그룹글
	    List<GroupBoard> groupBoards = groupBoardRepository.findByUserId_UserId(userId);
	    for (GroupBoard gb : groupBoards) {
	        result.add(MyPostDTO.builder()
	            .postId(gb.getGroupPostId())
	            .groupId(gb.getStudyGroup().getId())
	            .title(gb.getTitle())
	            .nickname(gb.getUserId().getNickname())
	            .createdDate(gb.getRegDate().format(formatter))
	            .type("group")
	            .url("/group-board/" + gb.getStudyGroup().getId() + "/post/" + gb.getGroupPostId())
	            .build());
	    }

	    return result;

	}
}
