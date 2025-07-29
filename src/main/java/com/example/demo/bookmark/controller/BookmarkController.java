package com.example.demo.bookmark.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bookmark.service.BookmarkService;
import com.example.demo.login.util.JwtTokenProvider;
import com.example.demo.recruitboard.dto.RecruitBoardDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {
	
	private final BookmarkService bookmarkService;
    private final JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/{recruitPostId}")
    public ResponseEntity<?> addBookmark(
            @PathVariable int recruitPostId,
            @RequestHeader("Authorization") String token) {
        Long userId = jwtTokenProvider.extractUserId(token);
        bookmarkService.addBookmark(userId, recruitPostId);
        return ResponseEntity.ok("찜 추가 완료");
    }

    @DeleteMapping("/{recruitPostId}")
    public ResponseEntity<?> removeBookmark(
            @PathVariable int recruitPostId,
            @RequestHeader("Authorization") String token) {
        Long userId = jwtTokenProvider.extractUserId(token);
        bookmarkService.removeBookmark(userId, recruitPostId);
        return ResponseEntity.ok("찜 제거 완료");
    }

    @GetMapping("/check/{recruitPostId}")
    public ResponseEntity<Boolean> isBookmarked(
            @PathVariable int recruitPostId,
            @RequestHeader("Authorization") String token) {
        System.out.println("🔑 받은 토큰: " + token);

        Long userId = jwtTokenProvider.extractUserId(token);
        System.out.println("🧍 사용자 ID: " + userId);

        return ResponseEntity.ok(bookmarkService.isBookmarked(userId, recruitPostId));
    }

    @GetMapping
    public ResponseEntity<List<RecruitBoardDTO>> getMyBookmarks(
            @RequestHeader("Authorization") String token) {
        Long userId = jwtTokenProvider.extractUserId(token);
        return ResponseEntity.ok(bookmarkService.getMyBookmarks(userId));
    }

}
