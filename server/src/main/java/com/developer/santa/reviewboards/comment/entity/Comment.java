package com.developer.santa.reviewboards.comment.entity;

import com.developer.santa.audit.Auditable;
import com.developer.santa.member.entity.Member;
import com.developer.santa.reviewboards.entity.ReviewBoard;
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
public class Comment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String commentId;

    @Column
    private String commentBody;

    @ManyToOne
    @JoinColumn(name="memberId")
    private Member member;

    @ManyToOne
    @JoinColumn(name="reviewBoardId")
    private ReviewBoard reviewBoard;
}
