package com.example.demo.user.service;

import java.util.List;

import com.example.demo.user.dto.MyPostDTO;

public interface MyPostService {

    List<MyPostDTO> getAllMyPosts(Long userId);

}
