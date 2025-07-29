package com.example.demo.bookmark.reopsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.bookmark.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

	List<Bookmark> findByUser_UserId(Long userId);
	
    boolean existsByUser_UserIdAndRecruitBoard_RecruitPostId(Long userId, int recruitPostId);
    
    void deleteByUser_UserIdAndRecruitBoard_RecruitPostId(Long userId, int recruitPostId);
    
}
