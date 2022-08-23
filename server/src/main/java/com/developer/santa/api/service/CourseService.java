package com.developer.santa.api.service;

import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.domain.course.CourseRepository;
import com.developer.santa.api.domain.mountain.Mountain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public List<Course> getCourse() {
        return courseRepository.findAll();
    }
}
