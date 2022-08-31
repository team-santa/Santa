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

    //todo page
    @GetMapping("")
    public ResponseEntity<List<CourseDTO>> getCourse(){
        return ResponseEntity.ok(courseService.getCourse());
    }

    @GetMapping("/selection")
    public ResponseEntity<List<CourseDTO>> getSelectCourse(
            @RequestParam String mountainName
    ){
        return ResponseEntity.ok(courseService.getMountainSelectCourse(mountainName));
    }

    @GetMapping("/{course}")
    public ResponseEntity<Course> getDetailCourse(@PathVariable String course){
        return ResponseEntity.ok(courseService.getDetailCourse(course));
    }
}
