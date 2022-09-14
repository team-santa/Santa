package com.developer.santa.member.annotation;

import com.developer.santa.member.oauth.entity.PrincipalDetails;
import com.developer.santa.member.oauth.entity.RoleType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        PrincipalDetails principalDetails = new PrincipalDetails("1234567", RoleType.USER, Collections.singletonList(new SimpleGrantedAuthority(RoleType.USER.getCode())));
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, "NO_PASS", principalDetails.getAuthorities());
        context.setAuthentication(authentication);

        return context;
    }
}
