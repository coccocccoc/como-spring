package com.example.demo.bookmark.service;

import java.util.List;

import com.example.demo.recruitboard.dto.RecruitBoardDTO;

public interface BookmarkService {
	
	void addBookmark(Long userId, int recruitPostId);
	
    void removeBookmark(Long userId, int recruitPostId);
    
    boolean isBookmarked(Long userId, int recruitPostId);
    
    List<RecruitBoardDTO> getMyBookmarks(Long userId);

}
