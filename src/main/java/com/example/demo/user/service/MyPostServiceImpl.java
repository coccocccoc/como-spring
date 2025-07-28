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

	    // ✅ 모집글 순회
	    List<RecruitBoard> recruitBoards = recruitBoardRepository.findByWriter_UserId(userId);
	    for (RecruitBoard rb : recruitBoards) {
	        result.add(MyPostDTO.builder()
	        	.postId(rb.getRecruitPostId())
	            .title(rb.getTitle())
	            .nickname(rb.getWriter().getNickname())
	            .createdDate(rb.getRegDate().format(formatter))
	            .type("모집글")
	            .build());
	    }

	    // ✅ 그룹 게시판 글 순회 (예시)
	    List<GroupBoard> groupBoards = groupBoardRepository.findByUserId_UserId(userId);
	    for (GroupBoard gb : groupBoards) {
	        result.add(MyPostDTO.builder()
	        	.postId(gb.getGroupPostId())
	            .title(gb.getTitle())
	            .nickname(gb.getUserId().getNickname())
	            .createdDate(gb.getRegDate().format(formatter))
	            .type("그룹글")
	            .build());
	    }

	    return result;
	}


}
