package com.developer.santa.reviewboards.comment.mapper;

import com.developer.santa.member.entity.Member;
import com.developer.santa.reviewboards.comment.dto.CommentRequestDto;
import com.developer.santa.reviewboards.comment.dto.CommentResponseDto;
import com.developer.santa.reviewboards.comment.entity.Comment;
import com.developer.santa.reviewboards.entity.ReviewBoard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    default Comment commentPostToComment(CommentRequestDto.Post requestBody){
        Comment comment = new Comment();
        ReviewBoard reviewBoard = new ReviewBoard();
        Member member = new Member();
        reviewBoard.setReviewBoardId(requestBody.getReviewBoardId());
        member.setMemberId(requestBody.getMemberId());

        comment.setCommentBody(requestBody.getCommentBody());
        comment.setReviewBoard(reviewBoard);
        comment.setMember(member);
        return comment;
    }
    default Comment commentPatchToComment(CommentRequestDto.Patch requestBody){
        Comment comment = new Comment();
        Member member = new Member();
        member.setMemberId(requestBody.getMemberId());
        comment.setCommentBody(requestBody.getCommentBody());
        comment.setMember(member);
        return comment;
    };

    default CommentResponseDto commentToResponseComment(Comment comment){
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setCommentId(comment.getCommentId());
        commentResponseDto.setMemberId(comment.getMember().getMemberId());
        commentResponseDto.setProfileImageUrl(comment.getMember().getProfileImageUrl());
        commentResponseDto.setWriter(comment.getMember().getUsername());
        commentResponseDto.setModifiedAt(comment.getModifiedAt());
        commentResponseDto.setBody(comment.getCommentBody());
        return commentResponseDto;
    }

}
