package com.example.demo.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	@Query("SELECT c FROM Comment c WHERE c.groupPostId.groupPostId = :postId ORDER BY c.commentId DESC")
	List<Comment> findByGroupPostIdOrderByCommentIdDesc(@Param("postId") int postId);

}
