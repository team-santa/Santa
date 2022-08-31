package com.developer.santa.reviewboards.mapper;

import com.developer.santa.reviewboards.dto.ReviewBoardRequestDto;
import com.developer.santa.reviewboards.dto.ReviewBoardResponseDto;
import com.developer.santa.reviewboards.entity.ReviewBoard;
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
