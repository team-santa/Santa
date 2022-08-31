package com.developer.santa.api.domain.local;

import com.developer.santa.api.domain.course.Course;
import com.developer.santa.api.domain.course.CourseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LocalMapper {
    List<LocalDTO> LocalListToLocalDTOList(List<Local> locals);
}
