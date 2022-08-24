package com.developer.santa.api.controller;

import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("")
    public ResponseEntity<List<Course>> getCourse(){
        return ResponseEntity.ok(courseService.getCourse());
    }

    @GetMapping("/selection")
    public ResponseEntity<List<Course>> getSelectCourse(
            @RequestParam String mountainName
    ){
        return ResponseEntity.ok(courseService.getMountainSelectCourse(mountainName));
    }
}
