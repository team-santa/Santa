package com.developer.santa.boards.mapper;

import com.developer.santa.boards.dto.ReviewBoardRequestDto;
import com.developer.santa.boards.dto.ReviewBoardResponseDto;
import com.developer.santa.boards.entity.ReviewBoard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewBoardMapper {

    ReviewBoard reviewBoardPostToReviewBoard(ReviewBoardRequestDto.Post requestBody);
    ReviewBoard reviewBoardPatchToReviewBoard(ReviewBoardRequestDto.Patch requestBody);

    ReviewBoardResponseDto.Page reviewBoardToPage(ReviewBoard reviewBoard);
    List<ReviewBoardResponseDto.Page> reviewBoardListToPages(List<ReviewBoard> reviewBoards);


}
