package com.example.demo.groupboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.groupboard.entity.GroupBoard;

public interface GroupBoardRepository extends JpaRepository<GroupBoard, Integer> {	

}
