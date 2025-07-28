package com.example.demo.studygroup.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/studies")
public class StudyGroupController {

    private final List<Map<String, Object>> studies = new ArrayList<>();

    @GetMapping
    public List<Map<String, Object>> getStudies() {
        return studies;
    }

    @PostMapping
    public Map<String, Object> addStudy(@RequestBody Map<String, Object> study) {
        studies.add(study);
        return study;
    }
}
