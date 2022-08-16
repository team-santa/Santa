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

    @Column(unique = true)
    private String userId;
    private String username;
    private String password;
    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    public Member(String userId, String username, String email, LocalDateTime createdAt, String profileImageUrl, ProviderType providerType, RoleType roleType) {
        this.userId = userId;
        this.username = username;
        this.password = "NO_PASS";
        this.email = email != null ? email : "NO_EMAIL";
        this.createdAt = createdAt;
        this.profileImageUrl = profileImageUrl != null ? profileImageUrl : "";
        this.providerType = providerType;
        this.roleType = roleType;
    }
}