package com.developer.santa.member.mapper;

import com.developer.santa.member.dto.MemberDto;
import com.developer.santa.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberDto.Response memberToMemberDtoResponse(Member member);
    Member memberPutDtoToMember(MemberDto.Put memberPutDto);
}
