package com.example.demo.techstack.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.techstack.entity.TechStack;

public interface TechStackRepository extends JpaRepository<TechStack, Integer> {

    // 필요시 이름으로 검색 등 확장 가능
    TechStack findByName(String name);
}
