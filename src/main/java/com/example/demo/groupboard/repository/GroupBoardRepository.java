package com.example.demo.groupboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.groupboard.entity.GroupBoard;

public interface GroupBoardRepository extends JpaRepository<GroupBoard, Integer> {	

    // 특정 그룹 ID에 속한 게시글 목록 조회 (페이징 처리 포함)
    Page<GroupBoard> findByGroupId_GroupId(int groupId, Pageable pageable);
}
