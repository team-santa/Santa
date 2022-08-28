package com.developer.santa.member.controller;

import com.developer.santa.member.dto.MemberDto;
import com.developer.santa.member.entity.Member;
import com.developer.santa.member.mapper.MemberMapper;
import com.developer.santa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto.Response> getMember(@PathVariable String memberId) {
        Member member = memberService.findMember(memberId);
        return ResponseEntity.ok().body(mapper.memberToMemberDtoResponse(member));
    }
}
