package com.developer.santa.reviewboards.comment.controller;


import com.developer.santa.reviewboards.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping // 댓글 생성
    public ResponseEntity<?> createComment( @Valid @RequestBody RequestBody requestBody){
        return null;
    }
    @GetMapping("{reviewBoardId}") // 게시판 전체 댓글 조회
    public ResponseEntity<?> findComment(@PathVariable Long reviewBoardId){
        return null;
    }

    @PatchMapping("{commentId}") // 댓글 수정
    public ResponseEntity<?> editComment(
            @PathVariable String commentId,
            @Valid @RequestBody RequestBody requestBody){ //변경필요
        return null;
    }

    @DeleteMapping("{commentId}") // 댓글 삭제
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId){
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
