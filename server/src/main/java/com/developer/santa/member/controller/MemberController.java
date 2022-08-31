package com.developer.santa.member.controller;

import com.developer.santa.member.dto.MemberDto;
import com.developer.santa.member.entity.Member;
import com.developer.santa.member.mapper.MemberMapper;
import com.developer.santa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto.Response> getMember(@PathVariable String memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(mapper.memberToMemberDtoResponse(member), HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberDto.Response> putMember(@PathVariable String memberId,
                                                        @RequestBody MemberDto.Put memberPutDto) {
        Member member = memberService.putMember(memberId, mapper.memberPutDtoToMember(memberPutDto));
        return new ResponseEntity<>(mapper.memberToMemberDtoResponse(member), HttpStatus.CREATED);
    }

    @GetMapping("/{username}/check")
    public ResponseEntity<Boolean> checkDuplicateUsername(@PathVariable String username) {
        return new ResponseEntity<>(!memberService.checkDuplicateUsername(username), HttpStatus.OK);
    }
}
