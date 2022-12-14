package com.developer.santa.reviewboards.mapper;

import com.developer.santa.member.entity.Member;
import com.developer.santa.member.service.MemberService;
import com.developer.santa.reviewboards.comment.dto.CommentResponseDto;
import com.developer.santa.reviewboards.comment.entity.Comment;
import com.developer.santa.reviewboards.dto.ReviewBoardRequestDto;
import com.developer.santa.reviewboards.dto.ReviewBoardResponseDto;
import com.developer.santa.reviewboards.entity.ReviewBoard;
import com.developer.santa.reviewboards.repository.ReviewBoardRepository;
import com.developer.santa.tag.entity.Tag;
import com.developer.santa.tag.entity.TagSelect;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewBoardMapper {

    default ReviewBoard reviewBoardPostToReviewBoard(ReviewBoardRequestDto.Post requestBody) {
        ReviewBoard reviewBoard = new ReviewBoard();
        Member member = new Member();
        member.setMemberId(requestBody.getMemberId());
        reviewBoard.setViews(0L);
        reviewBoard.setTitle(requestBody.getTitle());
        reviewBoard.setBody(requestBody.getBody());
        reviewBoard.setThumbnail(requestBody.getThumbnail());
        reviewBoard.setLocalName(requestBody.getLocalName());
        reviewBoard.setMountainName(requestBody.getMountainName());
        reviewBoard.setCourseName(requestBody.getCourseName());
        reviewBoard.setTags(requestBody.getTagList());
        List<TagSelect> tagSelects = requestBody.getTagList().stream()
                .map(tagSelectDto -> {
                    TagSelect tagSelect = new TagSelect();
                    Tag tag = new Tag();
                    tag.setTagName(tagSelectDto);
                    tagSelect.addTag(tag);
                    tagSelect.addReviewBoard(reviewBoard);
                    return tagSelect;
                }).collect(Collectors.toList());
        reviewBoard.setMember(member);
        reviewBoard.setTagSelects(tagSelects);
        return reviewBoard;
    }

    ;

    default ReviewBoard reviewBoardPatchToReviewBoard(ReviewBoardRequestDto.Patch requestBody) {
        ReviewBoard reviewBoard = new ReviewBoard();
        reviewBoard.setTitle(requestBody.getTitle());
        reviewBoard.setBody(requestBody.getBody());
        reviewBoard.setLocalName(requestBody.getLocalName());
        reviewBoard.setMountainName(requestBody.getMountainName());
        reviewBoard.setCourseName(requestBody.getCourseName());
        reviewBoard.setThumbnail(requestBody.getThumbnail());
        reviewBoard.setTags(requestBody.getTagList());
        List<TagSelect> tagSelects = requestBody.getTagList().stream()
                .map(tagSelectDto -> {
                    TagSelect tagSelect = new TagSelect();
                    Tag tag = new Tag();
                    tag.setTagName(tagSelectDto);
                    tagSelect.addTag(tag);
                    tagSelect.addReviewBoard(reviewBoard);
                    return tagSelect;
                }).collect(Collectors.toList());
        reviewBoard.setTagSelects(tagSelects);

        return reviewBoard;
    }

    ;

    default ReviewBoardResponseDto.Page reviewBoardToPage(ReviewBoard reviewBoard) {
        ReviewBoardResponseDto.Page reviewPage = new ReviewBoardResponseDto.Page();
        reviewPage.setReviewBoardId(reviewBoard.getReviewBoardId());
        //????????? ?????????, ?????? ??????, ?????????, ???????????????
        reviewPage.setWriterAndId(reviewBoard.getMember());
        reviewPage.setProfileImageUrl(reviewBoard.getMember().getProfileImageUrl());
        reviewPage.setModifiedAt(reviewBoard.getModifiedAt());
        reviewPage.setViews(reviewBoard.getViews());
        reviewPage.setTagList(reviewBoard.getTags());
        reviewPage.setTitle(reviewBoard.getTitle());
        reviewPage.setThumbnail(reviewBoard.getThumbnail());


        return reviewPage;
    }

    ;

    List<ReviewBoardResponseDto.Page> reviewBoardListToPages(List<ReviewBoard> reviewBoards);


    default ReviewBoardResponseDto.Detail reviewBoardToDetail(ReviewBoard reviewBoard) {
        ReviewBoardResponseDto.Detail reviewDetail = new ReviewBoardResponseDto.Detail();
        reviewDetail.setReviewBoardId(reviewBoard.getReviewBoardId());
        // ????????? ?????????, ????????????
        reviewDetail.setWriterAndId(reviewBoard.getMember());
        reviewDetail.setProfileImageUrl(reviewBoard.getMember().getProfileImageUrl());
        reviewDetail.setModifiedAt(reviewBoard.getModifiedAt());
        reviewDetail.setTitle(reviewBoard.getTitle());
        reviewDetail.setBody(reviewBoard.getBody());
        reviewDetail.setThumbnail(reviewBoard.getThumbnail());
        reviewDetail.setLocalName(reviewBoard.getLocalName());
        reviewDetail.setMountainName(reviewBoard.getMountainName());
        reviewDetail.setCourseName(reviewBoard.getCourseName());
        reviewDetail.setViews(reviewBoard.getViews());
        reviewDetail.setTagList(reviewBoard.getTags());
        List<CommentResponseDto> commentList = reviewBoard.getComments().stream()
                .map(comment -> {
                            CommentResponseDto dto = new CommentResponseDto();
                            dto.setCommentId(comment.getCommentId());
                            dto.setMemberId(comment.getMember().getMemberId());
                            dto.setProfileImageUrl(comment.getMember().getProfileImageUrl());
                            dto.setWriter(comment.getMember().getUsername());
                            dto.setModifiedAt(comment.getModifiedAt());
                            dto.setBody(comment.getCommentBody());
                            return dto;
                        }
                ).collect(Collectors.toList());
        reviewDetail.setCommentList(commentList);
        return reviewDetail;
    }

    ;
}
