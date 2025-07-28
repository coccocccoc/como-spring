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

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
    MyPostService myPostService;

	@Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("/my-posts")
    public List<MyPostDTO> getAllMyPosts(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        Long userId = Long.parseLong(jwtTokenProvider.getUserIdFromToken(token));
        return myPostService.getAllMyPosts(userId);
    }

}
