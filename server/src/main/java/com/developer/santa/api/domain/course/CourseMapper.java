package com.developer.santa.api.domain.course;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    List<CourseDTO> CourseListToCourseDTOList(List<Course> courses);
}
