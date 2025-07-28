package com.example.demo.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.dto.UserDTO;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	@Autowired
    UserService userService;
    
	@Autowired
	UserRepository userRepository;

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User user) {
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }
	    return ResponseEntity.ok(userService.entityToDto(user));
	}

    
    @PatchMapping("/update-nickname")
    public ResponseEntity<?> updateNickname(@AuthenticationPrincipal User userPrincipal,
                                            @RequestBody Map<String, String> request) {
        String newNickname = request.get("nickname");

        userRepository.updateNickname(userPrincipal.getUserId(), newNickname); // 💡 직접 업데이트 쿼리 실행
        return ResponseEntity.ok().build();
    }




    @PatchMapping("/update-image")
    public ResponseEntity<?> updateProfileImage(@AuthenticationPrincipal User userPrincipal,
                                                @RequestBody Map<String, String> request) {
        User user = userRepository.findById(userPrincipal.getUserId())
                        .orElseThrow(() -> new RuntimeException("사용자 없음"));
        user.setProfileImage(request.get("profileImage"));
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/update-email")
    public ResponseEntity<?> updateEmail(@AuthenticationPrincipal User userPrincipal,
                                         @RequestBody Map<String, String> request) {
        String newEmail = request.get("email");

        if (newEmail == null || newEmail.isBlank()) {
            return ResponseEntity.badRequest().body("이메일은 비어 있을 수 없습니다.");
        }

        userService.updateEmail(userPrincipal.getUserId(), newEmail);
        return ResponseEntity.ok().build();
    }
    
    @PatchMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@AuthenticationPrincipal User userPrincipal,
                                           @RequestBody UserDTO dto) {

        // 닉네임 수정
        if (dto.getNickname() != null && !dto.getNickname().isBlank()) {
            userRepository.updateNickname(userPrincipal.getUserId(), dto.getNickname());
        }

        // 이메일 수정
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            if (!dto.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                return ResponseEntity.badRequest().body("올바른 이메일 형식이 아닙니다.");
            }
            userService.updateEmail(userPrincipal.getUserId(), dto.getEmail());
        }

        // 프로필 이미지 수정
        if (dto.getProfileImage() != null) {
            User user = userRepository.findById(userPrincipal.getUserId())
                    .orElseThrow(() -> new RuntimeException("사용자 없음"));
            user.setProfileImage(dto.getProfileImage());
            userRepository.save(user);
            System.out.println("📦 profileImage: " + dto.getProfileImage());
        }

        return ResponseEntity.ok().build();
    }




    @GetMapping("/list")
    public ResponseEntity<Page<UserDTO>> getUserList(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(userService.getList(page));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> readUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.read(userId));
    }
}

