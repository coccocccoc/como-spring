package com.example.demo.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.login.util.JwtTokenProvider;
import com.example.demo.user.dto.MyPostDTO;
import com.example.demo.user.service.MyPostService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.util.S3FileUtil;
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
    MyPostService myPostService;

	@Autowired
    JwtTokenProvider jwtTokenProvider;

	@Autowired
    UserService userService;
    
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	S3FileUtil fileUtil;
	
    @GetMapping("/my-posts")
    public List<MyPostDTO> getAllMyPosts(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = Long.parseLong(jwtTokenProvider.getUserIdFromToken(token));
        return myPostService.getAllMyPosts(userId);
    }
    

	@GetMapping("/me")
	public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User user) {
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }
	    return ResponseEntity.ok(userService.entityToDto(user));
	}

    
	@GetMapping("/check-nickname")
	public ResponseEntity<?> checkNickname(@RequestParam String nickname) {
	    boolean exists = userRepository.existsByNickname(nickname);
	    return ResponseEntity.ok(Map.of("duplicate", exists));
	}





//    @PatchMapping("/update-image")
//    public ResponseEntity<?> updateProfileImage(@AuthenticationPrincipal User userPrincipal,
//                                                @RequestBody Map<String, String> request) {
//        User user = userRepository.findById(userPrincipal.getUserId())
//                        .orElseThrow(() -> new RuntimeException("사용자 없음"));
//        user.setProfileImage(request.get("profileImage"));
//        userRepository.save(user);
//        return ResponseEntity.ok().build();
//    }
    
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
                                           @ModelAttribute UserDTO dto) {

        // 닉네임 수정
        if (dto.getNickname() != null && !dto.getNickname().isBlank()) {
            // 자기 자신의 닉네임은 중복으로 보지 않기
            User currentUser = userRepository.findById(userPrincipal.getUserId())
                    .orElseThrow(() -> new RuntimeException("사용자 없음"));

            if (!currentUser.getNickname().equals(dto.getNickname()) &&
                    userRepository.existsByNickname(dto.getNickname())) {
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body("이미 사용 중인 닉네임입니다.");
            }

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
        MultipartFile file = dto.getUploadFile();
        if (file != null && !file.isEmpty()) {
            String s3Url = fileUtil.fileUpload(file);
            dto.setImgPath(s3Url);
            userService.updateProfileImage(userPrincipal.getUserId(), s3Url);
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

