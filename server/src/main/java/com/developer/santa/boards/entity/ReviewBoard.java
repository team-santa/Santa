package com.developer.santa.boards.entity;

import com.developer.santa.member.entity.Member;

import com.developer.santa.tag.entity.TagSelect;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewBoardId;

    @ManyToOne
    @JoinColumn(name ="user_name")
    private Member nickName;

    @Column(length = 20, nullable = false)// 추후 enum으로 표현
    private String localName;

    @Column(length = 100, nullable = false)
    private String mountainName;

    @Column(length = 100, nullable = false)
    private String courseName;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 100)
    private String body;  //CLob
    @Column(length = 100)
    private String photo; //BLob

    @OneToMany(mappedBy = "reviewBoard")
    private List<TagSelect> tagSelects;

    @Builder
    public ReviewBoard(String localName, String mountainName, String courseName, String title, String body, String photo) {
        this.localName = localName;
        this.mountainName = mountainName;
        this.courseName = courseName;
        this.title = title;
        this.body = body;
        this.photo = photo;
    }

    public void addTagSelect(TagSelect tagSelect){
        this.tagSelects.add(tagSelect);
        if(tagSelect.getReviewBoard() != this){
            tagSelect.addReviewBoard(this);
        }
    }

    public void setNickName(Member nickName) {
        this.nickName = nickName;
    }
}
