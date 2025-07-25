package com.example.demo.studygroup.service;

import com.example.demo.recruitboard.dto.RecruitBoardDTO;
import com.example.demo.studygroup.entity.StudyGroup;

public interface StudyGroupService {

	StudyGroup createFromRecruitBoard(RecruitBoardDTO dto);
	
}
