package com.example.demo.StudyGroupMember.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.StudyGroupMember.dto.StudyGroupMemberDTO;
import com.example.demo.StudyGroupMember.entity.StudyGroupMember;
import com.example.demo.StudyGroupMember.entity.StudyGroupMember.status;
import com.example.demo.StudyGroupMember.repository.StudyGroupMemberRepository;
import com.example.demo.studygroup.entity.StudyGroup;
import com.example.demo.studygroup.repository.StudyGroupRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

@Service
public class StudyGroupMemberServiceImpl implements StudyGroupMemberService {
	
	@Autowired
    StudyGroupMemberRepository memberRepo;

    @Autowired
    StudyGroupRepository groupRepo;

    @Autowired
    UserRepository userRepo;

    @Override
    public void applyToStudyGroup(StudyGroupMemberDTO dto) {
        int groupId = dto.getGroupId();
        long userId = dto.getUserId();

        Optional<StudyGroupMember> existing =
            memberRepo.findByGroup_IdAndUser_UserId(groupId, userId);

        if (existing.isPresent()) {
            StudyGroupMember m = existing.get();
            if (m.getJoinStatus() == status.가입) {
                throw new IllegalStateException("이미 가입된 유저입니다.");
            } else if (m.getJoinStatus() == status.승인대기중) {
                throw new IllegalStateException("이미 신청 중입니다.");
            }
        }

        StudyGroup group = groupRepo.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("그룹이 존재하지 않습니다."));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        StudyGroupMember member = StudyGroupMember.builder()
                .group(group)
                .user(user)
                .joinStatus(status.승인대기중)
                .applyTitle(dto.getApplyTitle())
                .applyContent(dto.getApplyContent())
                .build();

        memberRepo.save(member);
    }


    @Override
    public List<StudyGroupMemberDTO> getPendingMembersByGroupId(int groupId) {
        List<StudyGroupMember> members = memberRepo.findByGroup_IdAndJoinStatus(groupId, StudyGroupMember.status.승인대기중);
        return members.stream()
                .map(this::toDTO)  // StudyGroupMember -> DTO 변환 메서드 필요
                .collect(Collectors.toList());
    }

    @Override
    public void approveMember(int memberId) {
        StudyGroupMember member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
        member.setJoinStatus(StudyGroupMember.status.가입);
        memberRepo.save(member);
    }

    @Override
    public void rejectMember(int memberId) {
        StudyGroupMember member = memberRepo.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 멤버가 존재하지 않습니다."));
        member.setJoinStatus(StudyGroupMember.status.미가입);  // 또는 삭제도 가능
        memberRepo.save(member);
    }
    
    @Override
    public StudyGroupMemberDTO getApplication(int groupId, int userId) {
        System.out.println("🔥 호출됨 - groupId: " + groupId + ", userId: " + userId);

        StudyGroupMember member = memberRepo.findByGroup_IdAndUser_UserId(groupId, userId)
            .orElseThrow(() -> {
                System.out.println("❗ 신청 정보 없음");
                return new RuntimeException("해당 신청 정보를 찾을 수 없습니다.");
            });

        if (member.getUser() == null || member.getGroup() == null) {
            System.out.println("❌ Null 참조 있음: user=" + member.getUser() + ", group=" + member.getGroup());
        }
        
        return this.toDTO(member);
    }



}
