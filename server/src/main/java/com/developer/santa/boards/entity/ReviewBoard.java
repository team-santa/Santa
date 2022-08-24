package com.developer.santa.boards.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long reviewBoardId;


    @Column(length = 20, nullable = false)// 추후 enum으로 표현
    public String localName; // 1~16

    @Column(length = 100, nullable = false)// 추후 enum으로 표현
    public String mountainName; // 0000 ~ 0681

    @Column(length = 100, nullable = false) // 추후 enum으로 표현
    public String courseName;  // 000 ~ 099

    @Column(length = 100, nullable = false)
    public String title;

    @Column(length = 100)
    public String body;  //CLob
    @Column(length = 100)
    public String photo; //BLob



    @Builder
    public ReviewBoard(String localName, String mountainName, String courseName, String title, String body, String photo) {
        this.localName = localName;
        this.mountainName = mountainName;
        this.courseName = courseName;
        this.title = title;
        this.body = body;
        this.photo = photo;
    }
}
