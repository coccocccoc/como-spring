package com.example.demo.recruitboard.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.recruitboard.entity.RecruitBoard.StudyMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RecruitBoardDTO {

    int recruitPostId; // 게시글 ID (수정/조회용)

    int groupId; // 스터디 그룹 ID (연결용)
    long userId; // 작성자 ID

    String title; // 제목
    String content; // 본문

    LocalDateTime regDate; // 작성 일시 (읽기 전용)

    int capacity; // 모집 인원

    StudyMode mode; // 진행 방식 (온라인 / 오프라인 / 온오프라인)

    LocalDate startDate; // 예상 시작일
    LocalDate endDate; // 예상 종료일

    LocalDate deadline; // 모집 마감일 (nullable = 상시 모집)

    List<Integer> techStackIds; // 기술 스택 ID 리스트

    List<String> techStackNames; // 기술 스택 이름 리스트 (조회 시)
}
