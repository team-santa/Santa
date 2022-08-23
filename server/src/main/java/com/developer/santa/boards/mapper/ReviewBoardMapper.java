package com.developer.santa.boards.mapper;

import com.developer.santa.boards.dto.ReviewBoardDto;
import com.developer.santa.boards.entity.ReviewBoard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface ReviewBoardMapper {


    ReviewBoard reviewBoardPostToReviewBoard(ReviewBoardDto.Post requestBody);

}
