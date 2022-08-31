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

    default ReviewBoardResponseDto.Page reviewBoardToPage(ReviewBoard reviewBoard){
        ReviewBoardResponseDto.Page reviewPage =new ReviewBoardResponseDto.Page();
        reviewPage.setReviewBoardId(reviewBoard.getReviewBoardId());
        reviewPage.setNickName(reviewBoard.getNickName());
        reviewPage.setTitle(reviewBoard.getTitle());
        reviewPage.setPhoto(reviewBoard.getPhoto());
        return reviewPage;
    };
    List<ReviewBoardResponseDto.Page> reviewBoardListToPages(List<ReviewBoard> reviewBoards);


}
