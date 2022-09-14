package com.developer.santa.api.service;

import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import com.developer.santa.api.domain.course.CourseMapper;
import com.developer.santa.api.domain.course.CourseRepository;
import com.developer.santa.api.domain.mountain.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final MountainRepository mountainRepository;
    private final CourseMapper courseMapper;


    public List<CourseDTO> getCourse() {
        return courseMapper.CourseListToCourseDTOList(courseRepository.findAll());
    }


    public List<CourseDTO> getMountainSelectCourse(String mountainName) {
        return courseMapper.CourseListToCourseDTOList(
                courseRepository.findByMountain(
                        mountainRepository.findByMountainName(mountainName)
                ));
    }

    public Course getDetailCourse(String courseName){
        return courseRepository.findByCourseName(courseName);
    }
}
