package com.developer.santa.reviewboards.mapper;

import com.developer.santa.member.entity.Member;
import com.developer.santa.member.service.MemberService;
import com.developer.santa.reviewboards.dto.ReviewBoardRequestDto;
import com.developer.santa.reviewboards.dto.ReviewBoardResponseDto;
import com.developer.santa.reviewboards.entity.ReviewBoard;
import com.developer.santa.tag.entity.Tag;
import com.developer.santa.tag.entity.TagSelect;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewBoardMapper {


    default ReviewBoard reviewBoardPostToReviewBoard(ReviewBoardRequestDto.Post requestBody){
        ReviewBoard reviewBoard = new ReviewBoard();
        reviewBoard.setViews(0L);
        reviewBoard.setTitle(requestBody.getTitle());
        reviewBoard.setBody(requestBody.getBody());
        reviewBoard.setLocalName(requestBody.getLocalName());
        reviewBoard.setMountainName(requestBody.getMountainName());
        reviewBoard.setCourseName(requestBody.getCourseName());
        reviewBoard.setTags(requestBody.getTagList());
//        List<TagSelect> tagSelects = requestBody.getTagList().stream()
//                .map(tagSelectDto ->{
//                   TagSelect tagSelect = new TagSelect();
//                   Tag tag = new Tag();
//                   tag.setTagName(tagSelectDto);
//                   tagSelect.addTag(tag);
//                   tagSelect.addReviewBoard(reviewBoard);
//                   return tagSelect;
//                }).collect(Collectors.toList());
//        reviewBoard.setTagSelects(tagSelects);
        return reviewBoard;
    };
    default ReviewBoard reviewBoardPatchToReviewBoard(ReviewBoardRequestDto.Patch requestBody){
        ReviewBoard reviewBoard = new ReviewBoard();
        reviewBoard.setTitle(requestBody.getTitle());
        reviewBoard.setBody(requestBody.getBody());
        reviewBoard.setLocalName(requestBody.getLocalName());
        reviewBoard.setMountainName(requestBody.getMountainName());
        reviewBoard.setCourseName(requestBody.getCourseName());
        reviewBoard.setTags(requestBody.getTagList());
        return reviewBoard;
    };

    default ReviewBoardResponseDto.Page reviewBoardToPage(ReviewBoard reviewBoard){
        ReviewBoardResponseDto.Page reviewPage =new ReviewBoardResponseDto.Page();
        reviewPage.setReviewBoardId(reviewBoard.getReviewBoardId());
        reviewPage.setNickName(reviewBoard.getNickName());
        reviewPage.setTitle(reviewBoard.getTitle());
        reviewPage.setPhoto(reviewBoard.getPhoto());
        return reviewPage;
    };
    List<ReviewBoardResponseDto.Page> reviewBoardListToPages(List<ReviewBoard> reviewBoards);


   default ReviewBoardResponseDto.Detail reviewBoardToDetail(ReviewBoard reviewBoard){
       ReviewBoardResponseDto.Detail reviewDetail =new ReviewBoardResponseDto.Detail();
       reviewDetail.setReviewBoardId(reviewBoard.getReviewBoardId());
       reviewDetail.setNickName(reviewBoard.getNickName());
       reviewDetail.setTitle(reviewBoard.getTitle());
       reviewDetail.setBody(reviewBoard.getBody());
       reviewDetail.setPhoto(reviewBoard.getPhoto());
       reviewDetail.setLocalName(reviewBoard.getLocalName());
       reviewDetail.setMountainName(reviewBoard.getMountainName());
       reviewDetail.setCourseName(reviewBoard.getCourseName());
       reviewDetail.setViews(reviewBoard.getViews());
       reviewDetail.setTagList(reviewBoard.getTags());

       return reviewDetail;
   };
}
