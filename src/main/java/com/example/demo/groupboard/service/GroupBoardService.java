package com.example.demo.groupboard.service;

import java.util.List;

import com.example.demo.groupboard.dto.GroupBoardDTO;

public interface GroupBoardService {

	// 게시물 등록
	int register();
	
	// 게시물 리스트 조회
	List<GroupBoardDTO> getGroupBoardList();
	
	// 게시물 상세 조회
	
}
