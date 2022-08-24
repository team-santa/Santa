package com.developer.santa.member.entity;

import com.developer.santa.member.oauth.entity.ProviderType;
import com.developer.santa.member.oauth.entity.RoleType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String memberId;

    @Column(length = 30, unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = true)
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;

    public Member(String memberId, String username, String email, LocalDateTime createdAt, String profileImageUrl, ProviderType providerType, RoleType roleType) {
        this.memberId = memberId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.createdAt = createdAt;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }
}