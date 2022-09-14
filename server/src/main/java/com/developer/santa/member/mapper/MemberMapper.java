package com.developer.santa.member.mapper;

import com.developer.santa.member.dto.MemberDto;
import com.developer.santa.member.entity.Member;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    MemberDto.Response memberToMemberDtoResponse(Member member);

    Member memberPutDtoToMember(MemberDto.Put memberPutDto);

    default MemberDto.Mountain memberToMemberDtoMountain(Member member) {

        List<String> mountains = member.getFavoriteMountains().stream()
                .map(favoriteMountain -> favoriteMountain.getMountain().getMountainName())
                .collect(Collectors.toList());

        return new MemberDto.Mountain(mountains);
    }
}
