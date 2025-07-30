package com.example.demo.studygroup.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudyGroupDTO {

    private int groupId;
    private Long creatorId;
    private String title;
    private String content;
    private String nickname;
    private LocalDateTime regDate;

    private int capacity;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate deadline;

    private String mode;
    private List<String> techStackNames;

    private String status; // 모집중, 활동중 등
    
    public StudyGroupDTO(int id, String title, long creatorId) {
        this.groupId = id;
        this.title = title;
        this.creatorId = creatorId;
    }

    
}
