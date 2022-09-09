package com.developer.santa.reviewboards.comment.service;

import com.developer.santa.member.entity.Member;
import com.developer.santa.member.service.MemberService;
import com.developer.santa.reviewboards.comment.entity.Comment;
import com.developer.santa.reviewboards.comment.repository.CommentRepository;
import com.developer.santa.reviewboards.entity.ReviewBoard;
import com.developer.santa.reviewboards.repository.ReviewBoardRepository;
import com.developer.santa.reviewboards.service.ReviewBoardService;
import com.developer.santa.reviewboards.specification.ReviewBoardSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    public final CommentRepository commentRepository;
    public final ReviewBoardService reviewBoardService;
    private final MemberService memberService;
    // 댓글 생성
    public Comment createComment(Comment comment){
        comment.setMember(memberService.findMember(comment.getMember().getMemberId()));
        Comment saveComment = commentRepository.save(comment);
        Long reviewBoardId = comment.getReviewBoard().getReviewBoardId();
        ReviewBoard reviewBoard = reviewBoardService.findVerifiedReviewBoard(reviewBoardId);

        //게시글의 댓글 갯수 추가
        reviewBoard.addCommentCount();
        reviewBoardService.updateMyBoard(reviewBoardId , reviewBoard);

        return saveComment;

    }
    // 댓글 수정
    public Comment updateComment(Long commentId, Comment editComment){
        Comment originComment = findVerifiedComment(commentId);
        Optional.ofNullable(editComment.getCommentBody()).ifPresent(originComment::setCommentBody);

        return commentRepository.save(originComment);
    }

    // 댓글 조회
    public List<Comment> findComments(Long reviewBoardId){
        ReviewBoard reviewBoard = reviewBoardService.findVerifiedReviewBoard(reviewBoardId);
        return reviewBoard.getComments();

    }

    //댓글 삭제
    public void deleteReviewBoard(Long commentId) {
        Comment comment = findVerifiedComment(commentId);
        commentRepository.delete(comment);
    }

   public Comment findVerifiedComment(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        return optionalComment.orElseThrow(
                () -> new IllegalArgumentException("존재하지않는 댓글입니다. 댓글번호 :" + commentId)
        );
    }

}
