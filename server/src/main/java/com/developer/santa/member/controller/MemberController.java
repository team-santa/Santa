package com.developer.santa.member.controller;

import com.developer.santa.boards.dto.ReviewBoardResponseDto;
import com.developer.santa.boards.mapper.ReviewBoardMapper;
import com.developer.santa.dto.SingleResponseDto;
import com.developer.santa.member.dto.MemberDto;
import com.developer.santa.member.entity.Member;
import com.developer.santa.member.mapper.MemberMapper;
import com.developer.santa.member.oauth.entity.PrincipalDetails;
import com.developer.santa.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;
    private final ReviewBoardMapper reviewBoardMapper;

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto.Response> getMember(@PathVariable String memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(memberMapper.memberToMemberDtoResponse(member), HttpStatus.OK);
    }

    @PutMapping("/{memberId}")
    public ResponseEntity<MemberDto.Response> putMember(@PathVariable String memberId,
                                                        @RequestBody MemberDto.Put memberPutDto,
                                                        @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                        HttpServletResponse response) {
        Member member = memberService.putMember(memberId, memberMapper.memberPutDtoToMember(memberPutDto), principalDetails, response);
        return new ResponseEntity<>(memberMapper.memberToMemberDtoResponse(member), HttpStatus.CREATED);
    }

    @GetMapping("/{username}/check")
    public ResponseEntity<SingleResponseDto<Boolean>> checkDuplicateUsername(@PathVariable String username) {
        return new ResponseEntity<>(new SingleResponseDto<>(!memberService.checkDuplicateUsername(username)), HttpStatus.OK);
    }

    @PostMapping("/{memberId}/mountains/{mountainName}")
    public ResponseEntity<MemberDto.Mountain> likeMountain(@PathVariable String memberId,
                                                           @PathVariable String mountainName) {
        Member member = memberService.postMemberFavoriteMountain(memberId, mountainName);
        return new ResponseEntity<>(memberMapper.memberToMemberDtoMountain(member), HttpStatus.CREATED);
    }

    @DeleteMapping("/{memberId}/mountains/{mountainName}")
    public ResponseEntity<Void> deleteMountain(@PathVariable String memberId,
                                               @PathVariable String mountainName) {
        memberService.deleteMemberFavoriteMountain(memberId, mountainName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{memberId}/mountains")
    public ResponseEntity<MemberDto.Mountain> getMountains(@PathVariable String memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(memberMapper.memberToMemberDtoMountain(member), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/reviewboards")
    public ResponseEntity<List<ReviewBoardResponseDto.Page>> getReviewBoards(@PathVariable String memberId) {
        Member member = memberService.findMember(memberId);
        return new ResponseEntity<>(reviewBoardMapper.reviewBoardListToPages(member.getReviewBoards()), HttpStatus.OK);
    }


}
