package com.developer.santa.api.domain.course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Boolean existsByCourseLocation(String courseLocation);
}
