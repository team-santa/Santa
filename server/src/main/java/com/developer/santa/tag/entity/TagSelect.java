package com.developer.santa.tag.entity;


import com.developer.santa.reviewboards.entity.ReviewBoard;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class TagSelect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagSelectId;

    @ManyToOne
    @JoinColumn(name = "REVIEW_BOARD_ID")
    private ReviewBoard reviewBoard;

    @ManyToOne
    @JoinColumn(name = "TAG_NAME")
    private Tag tag;

    public void addReviewBoard(ReviewBoard reviewBoard){
        this.reviewBoard = reviewBoard;
        if(!this.reviewBoard.getTagSelects().contains(this)){
            this.reviewBoard.getTagSelects().add(this);
        }
    }
    public void addTag(Tag tag){
        this.tag =tag;
        if(!this.tag.getTagSelects().contains(this)){
            this.tag.getTagSelects().add(this);
        }
    }

}
