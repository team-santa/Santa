package com.developer.santa.api.controller;

import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("")
    public ResponseEntity<CourseDTO> getCourse(){
        CourseDTO load = courseService.getCourse();
        return ResponseEntity.ok(load);
    }
}
