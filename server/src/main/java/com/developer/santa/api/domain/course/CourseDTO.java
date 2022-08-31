package com.developer.santa.api.domain.course;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseDTO {

    private String courseName;

    @Builder
    public CourseDTO(String courseName){
        this.courseName=courseName;
    }
}
