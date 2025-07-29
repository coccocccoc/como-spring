package com.example.demo.bookmark.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.bookmark.entity.Bookmark;
import com.example.demo.bookmark.reopsitory.BookmarkRepository;
import com.example.demo.recruitboard.dto.RecruitBoardDTO;
import com.example.demo.recruitboard.entity.RecruitBoard;
import com.example.demo.recruitboard.repository.RecruitBoardRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {
	
	@Autowired
	BookmarkRepository bookmarkRepository;
	
	@Autowired
    UserRepository userRepository;
    
	@Autowired
    RecruitBoardRepository recruitBoardRepository;

	@Override
    @Transactional
    public void addBookmark(Long userId, int recruitPostId) {
        if (bookmarkRepository.existsByUser_UserIdAndRecruitBoard_RecruitPostId(userId, recruitPostId)) {
            return; // 이미 존재하면 무시
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        RecruitBoard post = recruitBoardRepository.findById(recruitPostId)
                .orElseThrow(() -> new IllegalArgumentException("모집글 없음"));

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .recruitBoard(post)
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Override
    @Transactional
    public void removeBookmark(Long userId, int recruitPostId) {
        bookmarkRepository.deleteByUser_UserIdAndRecruitBoard_RecruitPostId(userId, recruitPostId);
    }

    @Override
    public boolean isBookmarked(Long userId, int recruitPostId) {
        return bookmarkRepository.existsByUser_UserIdAndRecruitBoard_RecruitPostId(userId, recruitPostId);
    }

    @Override
    public List<RecruitBoardDTO> getMyBookmarks(Long userId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUser_UserId(userId);
        return bookmarks.stream()
                .map(bookmark -> {
                    RecruitBoard post = bookmark.getRecruitBoard();
                    return RecruitBoardDTO.builder()
                            .recruitPostId(post.getRecruitPostId())
                            .groupId(post.getStudyGroup().getId())
                            .userId(post.getWriter().getUserId())
                            .nickname(post.getWriter().getNickname())
                            .title(post.getTitle())
                            .content(post.getContent())
                            .regDate(post.getRegDate())
                            .capacity(post.getCapacity())
                            .mode(post.getMode())
                            .startDate(post.getStartDate())
                            .endDate(post.getEndDate())
                            .deadline(post.getDeadline())
                            .techStackIds(post.getTechStacks().stream().map(ts -> ts.getTechStackId()).toList())
                            .techStackNames(post.getTechStacks().stream().map(ts -> ts.getName()).toList())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
