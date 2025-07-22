package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.comment.service.CommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/group-board/comments")
public class CommentController {
	
	CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    // 댓글 추가
    @PostMapping("/add")
    public ResponseEntity<CommentDTO> registerComment(@RequestBody Map<String, Object> payload) {
        try {
            int groupPostId = (int) payload.get("groupPostId");
            int userId = (int) payload.get("userId");
            String content = (String) payload.get("content");

            CommentDTO created = commentService.registerComment(userId, groupPostId, content);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    // 게시물 ID로 댓글 목록 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPostId(@PathVariable("postId") int postId) {
        List<CommentDTO> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }


    // 댓글 수정
    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable("commentId") int commentId,
            @RequestBody Map<String, Object> payload) {

        String content = (String) payload.get("content");
        CommentDTO commentDTO = commentService.updateComment(commentId, content);
        return ResponseEntity.ok(commentDTO);
    }


    // 댓글 삭제
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
