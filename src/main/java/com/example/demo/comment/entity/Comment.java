package com.example.demo.comment.entity;

import java.time.LocalDateTime;

import com.example.demo.groupboard.entity.GroupBoard;
import com.example.demo.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int commentId; // 댓글 ID
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_post_id", nullable = false)
	GroupBoard groupPostId; // 댓글이 달린 게시물
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	User userId; // 댓글 작성자
    
    @Column(nullable = false, columnDefinition = "TEXT")
	String content; // 내용
    
    LocalDateTime regDate; // 작성 일시
}
