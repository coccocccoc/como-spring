package com.example.demo.StudyGroupMember.entity;

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

// 가입 관계 테이블
// 스터디에 가입되어있는 회원들을 관리

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Table(name = "study_group_member")
public class StudyGroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    StudyGroup group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
    
    public enum status {
        가입, 승인대기중, 미가입
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    status joinStatus;

}
