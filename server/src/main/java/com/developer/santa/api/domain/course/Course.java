package com.developer.santa.api.domain.course;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String courseName;

    @Column
    private String courseLocation;

    @Column
    private String courseLevel;

    @Column
    private String courseDistance;

//    //mountain
//    @ManyToOne
//
//    //review
//    @OneToMany

    @Builder
    public Course(String courseName, String courseLocation, String courseLevel, String courseDistance){
        this.courseName = courseName;
        this.courseLocation = courseLocation;
        this.courseLevel = courseLevel;
        this.courseDistance = courseDistance;
    }
}
