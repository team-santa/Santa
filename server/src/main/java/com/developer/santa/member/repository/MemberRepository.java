package com.developer.santa.member.repository;

import com.developer.santa.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);

    Member findByUserId(String userId);
}
