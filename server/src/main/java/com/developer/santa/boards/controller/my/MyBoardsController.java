package com.developer.santa.boards.controller.my;

import com.developer.santa.boards.dto.ReviewBoardRequestDto;
import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.mapper.ReviewBoardMapper;
import com.developer.santa.boards.service.my.MyBoardService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<?> createMyReview(@Valid @RequestBody ReviewBoardRequestDto.Post requestBody){
        ReviewBoard reviewBoard = mapper.reviewBoardPostToReviewBoard(requestBody);
        ReviewBoard createBoard = myBoardService.createMyBoard(reviewBoard);
        return new ResponseEntity<>(createBoard, HttpStatus.CREATED);
    }

    @PutMapping("/{boardId}")
    public ResponseEntity<?> editMyReview(@PathVariable Long boardId,
                                      @Valid @RequestBody ReviewBoardRequestDto.Patch requestBody){
        ReviewBoard reviewBoard = mapper.reviewBoardPatchToReviewBoard(requestBody);
        ReviewBoard updateBoard = myBoardService.updateMyBoard(boardId, reviewBoard);
        return new ResponseEntity<>(updateBoard, HttpStatus.OK);
    }


    @GetMapping("/{reviewBoardId}")
    public HttpEntity<ReviewBoard> findMyReview(@PathVariable Long reviewBoardId) {
        ReviewBoard selectMyBoard = myBoardService.selectMyBoard(reviewBoardId);

        return new HttpEntity<>(selectMyBoard); // (필요한 정보만)mapper로 변경 필요
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
