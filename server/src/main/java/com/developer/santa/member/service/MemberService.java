package com.developer.santa.member.service;

import com.developer.santa.exception.BusinessLogicException;
import com.developer.santa.exception.ExceptionCode;
import com.developer.santa.member.entity.Member;
import com.developer.santa.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public Member findMember(String memberId) {
        return verifyMember(memberId);
    }

    public Member putMember(String memberId, Member newMember) {
        Member savedMember = verifyMember(memberId);

        Optional.ofNullable(newMember.getProfileImageUrl())
                .ifPresent(profileImageUrl -> savedMember.setProfileImageUrl(profileImageUrl));
        Optional.ofNullable(newMember.getUsername())
                .ifPresent(username -> savedMember.setUsername(username));

        return savedMember;
    }

    @Transactional(readOnly = true)
    public Member verifyMember(String memberId) {
        Optional<Member> optionalMember = memberRepository.findByMemberId(memberId);

        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }
}
