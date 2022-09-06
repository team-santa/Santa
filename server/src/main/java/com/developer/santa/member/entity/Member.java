package com.developer.santa.member.entity;

import com.developer.santa.audit.Auditable;
import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.member.oauth.entity.ProviderType;
import com.developer.santa.member.oauth.entity.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends Auditable implements Serializable {

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

    @OneToMany(mappedBy = "nickName", cascade = CascadeType.ALL)
    private List<ReviewBoard> reviewBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteMountain> favoriteMountains = new ArrayList<>();

    public Member(String memberId, String email, String profileImageUrl, ProviderType providerType, RoleType roleType) {
        this.memberId = memberId;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }

    public void addFavoriteMountain(FavoriteMountain favoriteMountain) {
        this.favoriteMountains.add(favoriteMountain);
        favoriteMountain.setMember(this);
    }

    public void deleteFavoriteMountain(String mountainName) {
        this.favoriteMountains.removeIf(favoriteMountain -> favoriteMountain.getMountain().getMountainName().equals(mountainName));
    }

    public void addReviewBoard(ReviewBoard reviewBoard){
        this.reviewBoards.add(reviewBoard);
        if(reviewBoard.getNickName() != this){
            reviewBoard.setNickName(this);
        }
    }
}