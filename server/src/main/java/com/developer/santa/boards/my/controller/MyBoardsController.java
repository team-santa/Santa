package com.developer.santa.boards.my.controller;

import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.my.service.MyBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("my/boards")
public class MyBoardsController {

    @Autowired
    private MyBoardService myBoardService;


    @GetMapping
    public HttpEntity<ReviewBoard> findMyAllReview() {
        List<ReviewBoard> allMyBoard = myBoardService.findAllMyBoard();

        // 멀티용 필요
        return new HttpEntity<>(null);
    }
    @GetMapping("/{boardId}")
    public HttpEntity<ReviewBoard> findMyReview(@PathVariable Long boardId) {
        ReviewBoard selectMyBoard = myBoardService.selectMyBoard(boardId);

        return new HttpEntity<>(selectMyBoard);
    }
    @PostMapping
    public HttpEntity<?> createMyReview(@RequestBody ReviewBoard reviewBoard){
        ReviewBoard myBoard = myBoardService.createMyBoard(reviewBoard);
        return new HttpEntity<>(myBoard);
    }



    @PutMapping("/{boardId}")
    public HttpEntity<?> editMyReview(@PathVariable Long boardId,
                                      @RequestBody ReviewBoard reviewBoard){
        ReviewBoard updateBoard = myBoardService.updateMyBoard(boardId, reviewBoard);
        return new HttpEntity<>(updateBoard);
    }

    @DeleteMapping("/{boardId}")
    public HttpEntity<?> deleteMyReview(@PathVariable Long boardId){
        myBoardService.deleteMyBoard(boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
