package com.developer.santa.api.domain.mountain;

import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MountainMapper {
    List<MountainDTO> MountainListToMountainDTOList(List<Mountain> mountains);
}
