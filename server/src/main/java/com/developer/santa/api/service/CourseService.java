package com.developer.santa.api.service;

import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.domain.course.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseDTO getCourse() {
        CourseDTO courseDTO = new CourseDTO();
        return courseDTO;
    }
}
