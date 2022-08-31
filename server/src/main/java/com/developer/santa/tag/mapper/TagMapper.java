package com.developer.santa.tag.mapper;

import com.developer.santa.tag.dto.TagResponseDto;
import com.developer.santa.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponseDto.Recommend tagToRecommend(Tag tag);
    List<TagResponseDto.Recommend> tagListToRecommendList(List<Tag> tag);
}
