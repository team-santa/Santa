package com.developer.santa.member.oauth.entity;

import com.developer.santa.member.entity.Member;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User, OidcUser {

    private String userId;

    private String password;

    private ProviderType providerType;

    private RoleType roleType;
    private Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getClaims() {
        return null;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return null;
    }

    @Override
    public OidcIdToken getIdToken() {
        return null;
    }

    public PrincipalDetails(String userId, String password, ProviderType providerType, RoleType roleType, Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.password = password;
        this.providerType = providerType;
        this.roleType = roleType;
        this.authorities = authorities;
    }

    public PrincipalDetails(String userId, RoleType roleType, Collection<GrantedAuthority> authorities) {
        this.userId = userId;
        this.roleType = roleType;
        this.authorities = authorities;
    }

    public static PrincipalDetails create(Member user) {
        return new PrincipalDetails(
                user.getMemberId(),
                user.getPassword(),
                user.getProviderType(),
                RoleType.USER,
                Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode()))
        );
    }

    public static PrincipalDetails create(Member user, Map<String, Object> attributes) {
        PrincipalDetails userPrincipal = create(user);
        userPrincipal.setAttributes(attributes);

        return userPrincipal;
    }


}
