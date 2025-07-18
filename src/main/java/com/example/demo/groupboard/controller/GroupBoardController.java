package com.example.demo.groupboard.controller;

import com.example.demo.groupboard.dto.GroupBoardDTO;
import com.example.demo.groupboard.service.GroupBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group-board")
public class GroupBoardController {

    @Autowired
    private GroupBoardService groupBoardService;

    // 게시물 등록
    @PostMapping("/register")
    public ResponseEntity<Integer> registerPost(@RequestBody GroupBoardDTO dto) {
        int postId = groupBoardService.registerGroupPost(dto);
        return ResponseEntity.ok(postId);
    }

    // 게시물 목록 조회 (페이징)
    @GetMapping("/{groupId}")
    public ResponseEntity<Page<GroupBoardDTO>> getPosts(
            @PathVariable("groupId") int groupId,
            Pageable pageable) {
        Page<GroupBoardDTO> posts = groupBoardService.getGroupPostsList(groupId, pageable);
        return ResponseEntity.ok(posts);
    }

    // 게시물 단건 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<GroupBoardDTO> getPostDetail(@PathVariable("postId") int postId) {
        GroupBoardDTO dto = groupBoardService.findGroupPostById(postId);
        return ResponseEntity.ok(dto);
    }

    // 게시물 수정
    @PutMapping("/post/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("postId") int postId,
            @RequestBody GroupBoardDTO dto) {
        groupBoardService.updateGroupPost(postId, dto);
        return ResponseEntity.ok().build();
    }

    // 게시물 삭제
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable("postId") int postId,
            @RequestParam("requesterId") int requesterId) {
        groupBoardService.deleteGroupPost(postId, requesterId);
        return ResponseEntity.ok().build();
    }
}
