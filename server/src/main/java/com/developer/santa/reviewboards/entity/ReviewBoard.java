package com.developer.santa.reviewboards.entity;

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
    public Long id;

    @Column(length = 20, nullable = false)// 추후 enum으로 표현
    public String localName;

    @Column(length = 100, nullable = false)// 추후 enum으로 표현
    public String mountainName;

    @Column(length = 100, nullable = false) // 추후 enum으로 표현
    public String courseName;

    @Column(length = 100, nullable = false)
    public String title;

    @Lob
    public String body;  //CLob
    @Lob
    public String photo; //BLob


    public ReviewBoard(Long id, String localName, String mountainName, String courseName, String title) {
        this.id = id;
        this.localName = localName;
        this.mountainName = mountainName;
        this.courseName = courseName;
        this.title = title;
    }

}
