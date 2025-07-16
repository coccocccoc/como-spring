package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.groupboard.entity.GroupBoard;
import com.example.demo.groupboard.entity.GroupBoard.cat;
import com.example.demo.groupboard.repository.GroupBoardRepository;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.studygroup.repository.StudyGroupRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.entity.User.provider;
import com.example.demo.user.repository.UserRepository;

@SpringBootTest
public class GroupBoardRepoTest {

	@Autowired
	GroupBoardRepository groupBoardRepo;
	
	@Autowired
	StudyGroupRepository groupRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Test
	public void 리파지토리확인() {
		System.out.println("GroupBoardRepository: " + groupBoardRepo);
	}

	@Test
	public void 게시물등록() {
		
		// 1. 임시 사용자 생성
        User user = User.builder()
                .socialId("test_social_id")
                .nickname("테스트유저")
                .region("서울")
                .age(25)
                .role("USER")
                .socialProvider(provider.KAKAO)
                .build();

        user = userRepo.save(user);

        // 2. 임시 스터디 그룹 생성 (created_by FK에 user 지정)
        StudyGroup group = StudyGroup.builder()
                .createdBy(user)
                .build();

        group = groupRepo.save(group);

        // 3. 게시글 생성
        GroupBoard board = GroupBoard.builder()
                .groupId(group) // FK로 연결
                .userId(user)   // 작성자
                .category(cat.공지사항)
                .title("스터디 모집합니다")
                .content("같이 공부하실 분~")
                .regDate(LocalDateTime.now())
                .build();
        groupBoardRepo.save(board);
        
        GroupBoard board2 = GroupBoard.builder()
                .groupId(group) // FK로 연결
                .userId(user)   // 작성자
                .category(cat.인증방)
                .title("인증합니다")
                .content("어제공부함")
                .regDate(LocalDateTime.now())
                .build();
        groupBoardRepo.save(board2);
        
    }
	
	@Test
	public void 수정() {
		Optional<GroupBoard> optional = groupBoardRepo.findById(1);
		
		if(optional.isPresent()) {
			GroupBoard groupBoard = optional.get();
			groupBoard.setContent("내용수정~~");
			groupBoardRepo.save(groupBoard);
		}
	}
	
	@Test
	public void 삭제() {
		groupBoardRepo.deleteById(2);
	}
	
}
