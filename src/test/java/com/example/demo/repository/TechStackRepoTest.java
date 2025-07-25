package com.example.demo.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.example.demo.techstack.entity.TechStack;
import com.example.demo.techstack.repository.TechStackRepository;

import jakarta.transaction.Transactional;

// tech stack 초기값 설정
@SpringBootTest
public class TechStackRepoTest {

    @Autowired
    TechStackRepository techStackRepository;
    
    @Test
    @Transactional
    @Commit
    public void 기술스택초기값등록() {
        List<String> names = List.of(
        		"Javascript", "Typescript", "React", "Vue", "Node.js", "Spring", "Java",
        		"Next.js", "Nest.js", "Express", "Go", "C", "Python", "Django", "Swift",
        		"Jest", "Kotlin", "MySQL", "MongoDB", "PHP", "GraphQL", "Firebase",
        		"React Native", "Unity", "Flutter", "AWS", "Kubernetes", "Docker", "Git",
        		"Figma", "Zeplin", "Svelte"
        );

        names.forEach(name -> {
        	if (!techStackRepository.existsByName(name)) {
        	    techStackRepository.save(TechStack.builder().name(name).build());
            }
        });

        System.out.println("기술 스택 초기값 등록 완료 (" + names.size() + "개)");
    }
    
}
