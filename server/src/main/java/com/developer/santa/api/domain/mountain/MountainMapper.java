package com.developer.santa.api.domain.mountain;

import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MountainMapper {
    MountainDTO MountainToMountainDTO(Mountain mountain);
}
