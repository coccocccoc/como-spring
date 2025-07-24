package com.example.demo.comment.service;

import java.util.List;

import com.example.demo.comment.dto.CommentDTO;
import com.example.demo.comment.entity.Comment;
import com.example.demo.user.entity.User;

public interface CommentService {
	
	// 댓글 등록
	CommentDTO registerComment(long userId, int groupPostId, String content);

	// 특정 게시물에 대한 댓글 목록 조회
	List<CommentDTO> getCommentsByPostId(int groupPostId);
	
	// 댓글 수정
	CommentDTO updateComment(int commentId, String content);
	
	// 댓글 삭제
	void deleteComment(int commentId);
	
	// DTO → Entity
	default Comment toCommentEntity(CommentDTO dto) {
	    return Comment.builder()
	    		.commentId(dto.getCommentId())
	            .userId(User.builder().userId(dto.getUserId()).build())
	            .content(dto.getContent())
	            .regDate(dto.getRegDate())
	            .build();
	}
	
	// Entity → DTO
	default CommentDTO toCommentDTO(Comment entity) {
	    return CommentDTO.builder()
	    		.commentId(entity.getCommentId())
	            .userId(entity.getUserId().getUserId())
	            .nickname(entity.getUserId().getNickname())
	            .content(entity.getContent())
	            .regDate(entity.getRegDate())
	            .build();
	}
	
}
