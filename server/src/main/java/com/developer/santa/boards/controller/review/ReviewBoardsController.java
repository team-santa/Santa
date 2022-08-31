package com.developer.santa.boards.controller.review;

import com.developer.santa.boards.dto.ReviewBoardRequestDto;
import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.mapper.ReviewBoardMapper;
import com.developer.santa.boards.repository.ReviewBoardRepository;
import com.developer.santa.boards.service.review.ReviewBoardService;
import com.developer.santa.boards.specification.ReviewBoardSpecification;
import com.developer.santa.dto.MultiResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/reviewBoards")
@RequiredArgsConstructor
public class ReviewBoardsController {
    // 최신순조회
    private final ReviewBoardService reviewBoardService;
    private final ReviewBoardMapper mapper;

    @GetMapping
    public ResponseEntity<?> courseReview(@RequestParam(required = false) String city,
                                      @RequestParam(required = false) String mountain,
                                      @RequestParam(required = false) String course,
                                      @RequestParam int page) {


        Map<String, Object> spec = new HashMap<>();
        if (city != null)
            spec.put("localName" , city);
        if (mountain != null)
            spec.put("mountainName", mountain);
        if (course != null)
            spec.put("courseName", course);

        Page<ReviewBoard> reviewBoardPage = reviewBoardService.findReviewBoards(page-1, spec);
        List<ReviewBoard> reviewBoards = reviewBoardPage.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto(
                        mapper.reviewBoardListToPages(reviewBoards),
                        reviewBoardPage),
                HttpStatus.OK);
    }


//    @GetMapping("/{reviewBoardId}")
//    public ResponseEntity<ReviewBoard> findReviewBoard(@PathVariable Long reviewBoardId) {
//        ReviewBoard selectMyBoard = reviewBoardService.findReviewBoard(reviewBoardId);
//
//        return new ResponseEntity<>(selectMyBoard, HttpStatus.OK);
//    }

//    @PatchMapping("/{reviewBoardId}")
//    public ResponseEntity<?> editReviewBoard(@PathVariable Long reviewBoardId,
//                                          @Valid @RequestBody ReviewBoardRequestDto.Patch requestBody){
//        ReviewBoard reviewBoard = mapper.reviewBoardPatchToReviewBoard(requestBody);
//        ReviewBoard updateBoard = reviewBoardService.updateMyBoard(reviewBoardId, reviewBoard);
//        return new ResponseEntity<>(updateBoard, HttpStatus.OK);
//    }
//    @DeleteMapping("/{reviewBoardId}")
//    public ResponseEntity<?> deleteReviewBoard(@PathVariable Long reviewBoardId){
//        reviewBoardService.deleteReviewBoard(reviewBoardId);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
