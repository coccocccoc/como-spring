package com.example.demo.user.repository;

import com.example.demo.user.entity.User;
import com.example.demo.user.entity.User.provider;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;



public interface UserRepository extends JpaRepository<User, Long> {

	@Modifying
	@Transactional
	@Query("UPDATE User u SET u.nickname = :nickname WHERE u.userId = :userId")
	void updateNickname(@Param("userId") Long userId, @Param("nickname") String nickname);


    Optional<User> findBySocialIdAndSocialProvider(String socialId, provider socialProvider);
}
