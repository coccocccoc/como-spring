package com.example.demo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPostDTO {
	
	int postId;
	int groupId;
    String title;
    String nickname;
    String createdDate;
    String type; // 모집글 or 그룹글
    String url; // 이동할 URL 경로
    
    
}
