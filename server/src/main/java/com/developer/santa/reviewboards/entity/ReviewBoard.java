package com.developer.santa.reviewboards.entity;

import com.developer.santa.audit.Auditable;
import com.developer.santa.api.domain.course.Course;
import com.developer.santa.member.entity.Member;

import com.developer.santa.tag.entity.TagSelect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class ReviewBoard extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewBoardId;

    @Column(length = 20, nullable = false)// 추후 enum으로 표현
    private String localName;

    @Column(length = 100, nullable = false)
    private String mountainName;

    @Column(length = 100, nullable = false)
    private String courseName;

    @Column
    private Long views;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String body;
    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    @OneToMany(mappedBy = "reviewBoard", cascade = CascadeType.ALL)
    private List<TagSelect> tagSelects = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="courseId")
    private Course course;

    @ManyToOne
    @JoinColumn(name ="memberId")
    private Member member;

//    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
//    @JoinColumn(name ="imageId")
//    private List<Images> images = new ArrayList<>();

    @ElementCollection
    private List<String> viewers;

    @ElementCollection
    private List<String> tags;

    @Builder
    public ReviewBoard( String localName, String mountainName, String courseName,  String title, String body, String thumbnail, List<String> tags) {
        this.localName = localName;
        this.mountainName = mountainName;
        this.courseName = courseName;
        this.views = 0L;
        this.title = title;
        this.body = body;
        this.thumbnail = thumbnail;
        this.tags = tags;
    }

    public void addTagSelect(TagSelect tagSelect){
        this.tagSelects.add(tagSelect);
        if(tagSelect.getReviewBoard() != this){
            tagSelect.addReviewBoard(this);
        }
    }

    public void addViewCount(){
        this.views++;
    }
    public void addViewer(String viewer){
        this.viewers.add(viewer);
    }

//    public void deleteImage() {
//        this.images.clear();
//    }
}
