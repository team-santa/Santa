package com.developer.santa.api.service;

import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.domain.course.CourseRepository;
import com.developer.santa.api.domain.mountain.Mountain;
import com.developer.santa.api.domain.mountain.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final MountainRepository mountainRepository;

    public List<Course> getCourse() {
        return courseRepository.findAll();
    }

    public List<Course> getMountainSelectCourse(String mountainName) {
        return courseRepository.findByMountain(mountainRepository.findByMountainName(mountainName));
    }
}
