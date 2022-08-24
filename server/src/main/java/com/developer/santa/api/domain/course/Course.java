package com.developer.santa.api.domain.course;

import com.developer.santa.api.domain.local.Local;
import com.developer.santa.api.domain.mountain.Mountain;
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

    //mountain
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;


    @Builder
    public Course(String courseName, String courseLocation, String courseLevel, String courseDistance, Mountain mountain){
        this.courseName = courseName;
        this.courseLocation = courseLocation;
        this.courseLevel = courseLevel;
        this.courseDistance = courseDistance;
        this.mountain = mountain;
    }
}
