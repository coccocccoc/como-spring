package com.example.demo.studygroup.entity;

import com.example.demo.recruitboard.entity.RecruitBoard;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "study_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class StudyGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id; // 스터디그룹 아이디
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    User createdBy;
	
    public enum StudyStatus {
        모집중,
        활동중,
        종료
    }
	
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    StudyStatus status;

    @OneToOne(mappedBy = "studyGroup") // 주인이 아님
    RecruitBoard recruitBoard;
	
}
