package com.example.demo.repository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.groupboard.entity.GroupBoard;
import com.example.demo.groupboard.repository.GroupBoardRepository;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.studygroup.repository.StudyGroupRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.entity.User.provider;
import com.example.demo.user.repository.UserRepository;

@SpringBootTest
public class CommentRepoTest {

	@Autowired
	CommentRepository commentRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	GroupBoardRepository groupBoardRepo;
	
	@Autowired
	StudyGroupRepository groupRepo;
	
	@Test
	public void 리파지토리확인() {
		System.out.println("CommentRepository: " + commentRepo);
	}
	
	@Test
	public void 댓글등록() {
		
		// 임시 사용자 생성
	    User user = User.builder()
                .socialId("test_social_id")
                .nickname("테스트유저")
                .role("USER")
                .socialProvider(provider.KAKAO)
                .build();
        user = userRepo.save(user);
		
		// 임시 스터디 그룹 생성
        StudyGroup group = StudyGroup.builder()
                .createdBy(user)
                .build();
        
        group = groupRepo.save(group);
		
	    // 임시 게시물 생성
	    GroupBoard groupPost = GroupBoard.builder()
	            .title("게시물 제목")
	            .content("게시물 내용")
	            .build();
	    groupBoardRepo.save(groupPost);

		
		Comment comment = Comment.builder()
				.groupPostId(groupPost)
				.userId(user)
				.content("댓글")
				.regDate(LocalDateTime.now())
				.build();
		commentRepo.save(comment);
	}
	
}
