package com.example.demo.recruitboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.recruitboard.entity.RecruitBoard;

public interface RecruitBoardRepository extends JpaRepository<RecruitBoard, Integer> {

}
