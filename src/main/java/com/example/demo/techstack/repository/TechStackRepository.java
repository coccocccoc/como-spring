package com.example.demo.techstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.techstack.entity.TechStack;

public interface TechStackRepository extends JpaRepository<TechStack, Integer> {

	boolean existsByName(String name);

}
