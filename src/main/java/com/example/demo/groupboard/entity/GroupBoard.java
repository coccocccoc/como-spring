package com.example.demo.groupboard.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "group_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GroupBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int groupPostId; // 그룹 게시판 게시물 ID
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    StudyGroup studyGroup; // 그룹 ID
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User userId; // 작성자 ID
	
    public enum cat{
		공지사항, 자유방, 질문방, 인증방
	}
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	cat category; // 카테고리
	
	@Column(nullable = false, length = 225)
	String title; // 제목
	
	@Column(nullable = false, columnDefinition = "TEXT")
	String content; // 본문
	
	@CreationTimestamp
	@Column(updatable = false)
	LocalDateTime regDate; // 작성 일시

}
