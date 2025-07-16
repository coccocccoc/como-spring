package com.example.demo.StudyGroupMember.entity;

import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

// 가입 관계 테이블
// 스터디에 가입되어있는 회원들을 관리

@Entity
@Table(name = "study_group_member")
public class StudyGroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne 
    @JoinColumn(name = "group_id")
    StudyGroup group;

    @ManyToOne 
    @JoinColumn(name = "user_id")
    User user;
}
