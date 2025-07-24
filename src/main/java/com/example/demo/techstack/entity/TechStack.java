package com.example.demo.techstack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tech_stack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TechStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int techStackId;

    @Column(nullable = false, unique = true, length = 50)
    String name;  // Java, Spring, React 등
}
