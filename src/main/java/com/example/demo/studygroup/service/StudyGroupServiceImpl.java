package com.example.demo.studygroup.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.recruitboard.dto.RecruitBoardDTO;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.studygroup.entity.StudyGroup.StudyStatus;
import com.example.demo.studygroup.repository.StudyGroupRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

@Service
public class StudyGroupServiceImpl implements StudyGroupService {
	
	@Autowired
	StudyGroupRepository studyGroupRepo;
	
	@Autowired
	UserRepository userRepo;

	@Override
	public StudyGroup createFromRecruitBoard(RecruitBoardDTO dto) {
		
		LocalDate today = LocalDate.now();
        LocalDate deadline = dto.getDeadline();
        LocalDate start = dto.getStartDate();
        LocalDate end = dto.getEndDate();

        StudyStatus status;

        if (deadline == null || today.isBefore(deadline)) {
            status = StudyStatus.모집중;
        } else if (start != null && end != null &&
                   (today.isEqual(start) || today.isAfter(start)) &&
                   (today.isBefore(end) || today.isEqual(end))) {
            status = StudyStatus.활동중;
        } else {
            status = StudyStatus.종료;
        }

        // 유저 객체 조회
        User user = userRepo.findById(dto.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("해당 유저 없음"));

        StudyGroup group = StudyGroup.builder()
                .createdBy(user)
                .status(status)
                .build();

        return studyGroupRepo.save(group);
	}

}
