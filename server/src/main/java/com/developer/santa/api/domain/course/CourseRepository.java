package com.developer.santa.api.domain.course;

import com.developer.santa.api.domain.mountain.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Boolean existsByCourseLocation(String courseLocation);
    Boolean existsByCourseName(String courseName);
    List<Course> findByMountain(Mountain mountain);
    Course findByCourseName(String courseName);
}
