package com.developer.santa.member.service;

import com.developer.santa.api.domain.mountain.MountainRepository;
import com.developer.santa.exception.BusinessLogicException;
import com.developer.santa.exception.ExceptionCode;
import com.developer.santa.member.config.properties.AppProperties;
import com.developer.santa.member.entity.FavoriteMountain;
import com.developer.santa.member.entity.Member;
import com.developer.santa.member.oauth.entity.PrincipalDetails;
import com.developer.santa.member.oauth.token.AuthToken;
import com.developer.santa.member.oauth.token.AuthTokenProvider;
import com.developer.santa.member.repository.MemberRepository;
import com.developer.santa.utils.HeaderUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MountainRepository mountainRepository;

    private final AuthTokenProvider tokenProvider;

    private final AppProperties appProperties;

    @Transactional(readOnly = true)
    public Member findMember(String memberId) {
        return verifyMember(memberId);
    }

    public Member putMember(String memberId, Member newMember, PrincipalDetails principalDetails, HttpServletResponse response) {
        Member savedMember = verifyMember(memberId);

        Optional.ofNullable(newMember.getProfileImageUrl())
                .ifPresent(profileImageUrl -> savedMember.setProfileImageUrl(profileImageUrl));
        Optional.ofNullable(newMember.getUsername())
                .ifPresent(username -> savedMember.setUsername(username));

        AuthToken authToken = tokenProvider.createAuthToken(
                memberId,
                newMember.getUsername(),
                principalDetails.getRoleType().getCode(),
                new Date(new Date().getTime() + appProperties.getAuth().getTokenExpiry())
        );
        response.addHeader(HeaderUtils.HEADER_AUTHORIZATION, HeaderUtils.TOKEN_PREFIX + authToken.getToken());

        return savedMember;
    }

    public Member postMemberFavoriteMountain(String memberId, String mountainName) {
        Member member = findMember(memberId);
        FavoriteMountain favoriteMountain = new FavoriteMountain();
        favoriteMountain.setMountain(mountainRepository.findByMountainName(mountainName));
        member.addFavoriteMountain(favoriteMountain);

        return member;
    }

    public void deleteMemberFavoriteMountain(String memberId, String mountainName) {
        Member member = findMember(memberId);
        member.deleteFavoriteMountain(mountainName);
    }

    @Transactional(readOnly = true)
    public Member verifyMember(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Boolean checkDuplicateUsername(String username) {
        return memberRepository.existsByUsername(username);
    }


}
