package com.developer.santa.tag.entity;


import com.developer.santa.boards.entity.ReviewBoard;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TagSelect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagSelectId;

    @ManyToOne
    @JoinColumn(name = "review_board_id")
    private ReviewBoard reviewBoard;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    public void addReviewBoard(ReviewBoard reviewBoard){
        this.reviewBoard = reviewBoard;
        if(!this.reviewBoard.getTagSelect().contains(this)){
            this.reviewBoard.getTagSelect().add(this);
        }
    }
    public void addTag(Tag tag){
        this.tag =tag;
        if(this.tag.getTagSelects().contains(this)){
            this.tag.getTagSelects().add(this);
        }
    }

}
