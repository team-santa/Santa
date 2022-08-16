package com.developer.santa.member.oauth.service;

import com.developer.santa.member.entity.Member;
import com.developer.santa.member.oauth.entity.PrincipalDetails;
import com.developer.santa.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member memberEntity = memberRepository.findByUserId(username);
        System.out.println("username: " + username);
        if (memberEntity == null) {
            throw new UsernameNotFoundException("Can not find username");
        }

        return PrincipalDetails.create(memberEntity);
    }
}

