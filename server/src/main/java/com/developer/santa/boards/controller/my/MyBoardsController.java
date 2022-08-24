package com.developer.santa.boards.controller.my;

import com.developer.santa.boards.dto.ReviewBoardDto;
import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.mapper.ReviewBoardMapper;
import com.developer.santa.boards.service.my.MyBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("my/boards")
@RequiredArgsConstructor
public class MyBoardsController {

    private final MyBoardService myBoardService;
    private final ReviewBoardMapper mapper;


    @PostMapping
    public ResponseEntity<?> createMyReview(@Valid @RequestBody ReviewBoardDto.Post requestBody){
        ReviewBoard reviewBoard = mapper.reviewBoardPostToReviewBoard(requestBody);
        ReviewBoard createBoard = myBoardService.createMyBoard(reviewBoard);
        return new ResponseEntity<>(createBoard, HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> editMyReview(@PathVariable Long boardId,
                                      @Valid @RequestBody ReviewBoardDto.Patch requestBody){
        ReviewBoard reviewBoard = mapper.reviewBoardPatchToReviewBoard(requestBody);
        ReviewBoard updateBoard = myBoardService.updateMyBoard(boardId, reviewBoard);
        return new ResponseEntity<>(updateBoard, HttpStatus.OK);
    }


    @GetMapping("/{boardId}")
    public HttpEntity<ReviewBoard> findMyReview(@PathVariable Long boardId) {
        ReviewBoard selectMyBoard = myBoardService.selectMyBoard(boardId);

        return new HttpEntity<>(selectMyBoard);
    }


    @GetMapping
    public HttpEntity<ReviewBoard> findMyAllReview() {
        List<ReviewBoard> allMyBoard = myBoardService.findAllMyBoard();

        // 멀티용 필요
        return new HttpEntity<>(null);
    }


    @DeleteMapping("/{boardId}")
    public HttpEntity<?> deleteMyReview(@PathVariable Long boardId){
        myBoardService.deleteMyBoard(boardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
