package com.example.demo.recruitboard.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.techstack.entity.TechStack;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "recruit_board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RecruitBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int recruitPostId; // 스터디 모집 게시물 ID
	
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    User writer; // 작성자 ID
	
	@Column(nullable = false, length = 225)
	String title; // 제목
	
	@Column(nullable = false, columnDefinition = "TEXT")
	String content; // 본문
	
	@Column(updatable = false)
	LocalDateTime regDate; // 작성 일시
	
	@Column(nullable = false)
    int capacity; // 모집 인원
	
	public enum StudyMode {
        온라인, 오프라인, 온오프라인
    }
	
	@Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    StudyMode mode; // 진행 방식 (온라인 / 오프라인 / 온오프라인)
	
	@Column(nullable = false)
    LocalDate startDate; // 예상 시작일
	
	@Column(nullable = false)
    LocalDate endDate; // 예상 종료일

    @Column(nullable = true)
    LocalDate deadline; // 모집 마감일 (null인 경우 상시 모집)
    
    @ManyToMany
    @JoinTable(
        name = "recruit_board_tech_stack",
        joinColumns = @JoinColumn(name = "recruit_post_id"),
        inverseJoinColumns = @JoinColumn(name = "tech_stack_id")
    )
    List<TechStack> techStacks = new ArrayList<>(); // 기술 스택
	
}
