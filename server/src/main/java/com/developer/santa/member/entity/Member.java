package com.developer.santa.member.entity;

import com.developer.santa.audit.Auditable;
import com.developer.santa.member.oauth.entity.ProviderType;
import com.developer.santa.member.oauth.entity.RoleType;
import com.developer.santa.reviewboards.entity.ReviewBoard;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String memberId;

    @Column(length = 30, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(nullable = true)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

//    @OneToMany(mappedBy = "nickName", cascade = CascadeType.ALL) // 코드가 추가되서, 기존 로그인링크가 작동을 못함
//    private List<ReviewBoard> reviewBoards = new ArrayList<>();

//    public void addReviewBoard(ReviewBoard reviewBoard){
//        this.reviewBoards.add(reviewBoard);
//        if(reviewBoard.getNickName() != this){
//            reviewBoard.setNickName(this);
//        }
//    }
    public Member(String memberId, String email, String profileImageUrl, ProviderType providerType, RoleType roleType) {
        this.memberId = memberId;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }
}