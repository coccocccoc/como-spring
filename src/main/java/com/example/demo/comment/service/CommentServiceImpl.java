package com.example.demo.comment.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.groupboard.entity.GroupBoard;
import com.example.demo.groupboard.repository.GroupBoardRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	GroupBoardRepository groupBoardRepo;

	@Override
	public CommentDTO registerComment(int userId, int groupPostId, String content) {

	    if (content == null || content.trim().isEmpty()) {
	        System.out.println("[ERROR] 댓글 내용이 null 또는 빈 문자열입니다.");
	        throw new IllegalArgumentException("댓글 내용이 비어있습니다.");
	    }

	    User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
	    GroupBoard groupPost = groupBoardRepo.findById(groupPostId).orElseThrow(() -> new RuntimeException("Post not found"));

	    Comment comment = Comment.builder()
	            .userId(user)
	            .groupPostId(groupPost)
	            .content(content)
	            .build();

	    Comment savedComment = commentRepo.save(comment);

	    return toCommentDTO(savedComment);
	}

	@Override
	public List<CommentDTO> getCommentsByPostId(int groupPostId) {
	    List<Comment> comments = commentRepo.findByGroupPostIdOrderByCommentIdDesc(groupPostId);

	    return comments.stream()
	            .map(this::toCommentDTO)
	            .collect(Collectors.toList());
	}

	@Override
	public CommentDTO updateComment(int commentId, String content) {
        // 댓글 찾기
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // 댓글 내용 업데이트
        comment.setContent(content);

        // 댓글 저장
        Comment updatedComment = commentRepo.save(comment);

        // DTO로 변환하여 반환
        return toCommentDTO(updatedComment);
	}

	@Override
	public void deleteComment(int commentId) {
        // 댓글 찾기
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        // 댓글 삭제
        commentRepo.delete(comment);
	}

}
