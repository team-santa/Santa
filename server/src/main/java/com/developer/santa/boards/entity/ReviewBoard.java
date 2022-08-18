package com.developer.santa.boards.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long reviewBoardId;

    @Column(length = 20, nullable = false)// 추후 enum으로 표현
    public String localName;

    @Column(length = 100, nullable = false)// 추후 enum으로 표현
    public String mountainName;

    @Column(length = 100, nullable = false) // 추후 enum으로 표현
    public String courseName;

    @Column(length = 100, nullable = false)
    public String title;

    @Column(length = 100)
    public String body;  //CLob
    @Column(length = 100)
    public String photo; //BLob



    @Builder
    public ReviewBoard(Long reviewBoardId, String localName, String mountainName, String courseName, String title) {
        this.reviewBoardId = reviewBoardId;
        this.localName = localName;
        this.mountainName = mountainName;
        this.courseName = courseName;
        this.title = title;
    }

}
