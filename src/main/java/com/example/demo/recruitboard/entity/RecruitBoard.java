package com.example.demo.recruitboard.entity;

import java.time.LocalDateTime;

import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "recruit_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RecruitBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int recruitBoardId; // 스터디 모집 게시물 ID

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    StudyGroup groupId; // 그룹 ID
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User userId; // 작성자 ID
	
	@Column(nullable = false, length = 100)
	String title; // 제목
	
	@Column(nullable = false, columnDefinition = "TEXT")
	String content; // 본문
	
	LocalDateTime regDate; // 작성 일시
	
}
