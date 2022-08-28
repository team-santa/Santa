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
    @JsonManagedReference // 순환참조 방지
    @JoinColumn(name = "review_board_id")
    private ReviewBoard reviewBoard;

    @ManyToOne
    @JsonManagedReference // 순환참조 방지
    @JoinColumn(name = "tag_id")
    private Tag tag;


}
