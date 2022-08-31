package com.developer.santa.member.repository;

import com.developer.santa.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByUsername(String username);
    Optional<Member> findByMemberId(String memberId);
}
