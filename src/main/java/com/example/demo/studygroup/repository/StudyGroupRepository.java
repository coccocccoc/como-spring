package com.example.demo.studygroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.studygroup.entity.StudyGroup;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer> {

}
