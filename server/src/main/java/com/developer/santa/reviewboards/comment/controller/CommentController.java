package com.developer.santa.reviewboards.comment.controller;


import com.developer.santa.reviewboards.comment.dto.CommentRequestDto;
import com.developer.santa.reviewboards.comment.entity.Comment;
import com.developer.santa.reviewboards.comment.mapper.CommentMapper;
import com.developer.santa.reviewboards.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper mapper;
    @PostMapping // 댓글 생성
    public ResponseEntity<?> createComment( @Valid @RequestBody CommentRequestDto.Post requestBody){
        Comment comment = mapper.commentPostToComment(requestBody);
        Comment createComment = commentService.createComment(comment);
        return new ResponseEntity<>(mapper.commentToResponseComment(createComment), HttpStatus.CREATED);
    }
    @GetMapping("/{reviewBoardId}") // 게시판 전체 댓글 조회
    public ResponseEntity<?> findComment(@PathVariable Long reviewBoardId){
        return null;
    }

    @PatchMapping("/{commentId}") // 댓글 수정
    public ResponseEntity<?> editComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto.Patch requestBody){
        Comment editComment = mapper.commentPatchToComment(requestBody);
        Comment updateBoard = commentService.updateComment(commentId, editComment);
        return new ResponseEntity<>(mapper.commentToResponseComment(updateBoard), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}") // 댓글 삭제
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        commentService.deleteReviewBoard(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
