package com.developer.santa.member.oauth.token;

import com.developer.santa.member.oauth.entity.PrincipalDetails;
import com.developer.santa.member.oauth.entity.RoleType;
import com.developer.santa.member.oauth.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class AuthTokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "role";

    public AuthTokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(String id, String username, Date expiry) {
        return new AuthToken(id, username, expiry, key);
    }

    public AuthToken createAuthToken(String id, String username, String role, Date expiry) {
        return new AuthToken(id, username, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public Authentication getAuthentication(AuthToken authToken) {

        if(authToken.validate()) {

            Claims claims = authToken.getTokenClaims();
            String authority = claims.get(AUTHORITIES_KEY).toString();
            Collection<GrantedAuthority> authorities =
                    Arrays.stream(new String[]{authority})
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            log.debug("claims subject := [{}]", claims.getSubject());
            UserDetails userDetails = new PrincipalDetails(claims.getSubject(), RoleType.of(authority), authorities);

            return new UsernamePasswordAuthenticationToken(userDetails, authToken, authorities);
        } else {
            throw new TokenValidFailedException();
        }
    }
}