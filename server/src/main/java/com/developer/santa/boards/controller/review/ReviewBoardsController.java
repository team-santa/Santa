package com.developer.santa.boards.controller.review;

import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.repository.ReviewBoardRepository;
import com.developer.santa.boards.service.review.ReviewBoardService;
import com.developer.santa.boards.specification.ReviewBoardSpecification;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("review/boards")
@RequiredArgsConstructor
public class ReviewBoardsController {
    // 최신순조회
    private final ReviewBoardService reviewBoardService;
    private final ReviewBoardRepository reviewBoardRepository;

    @GetMapping
    public ResponseEntity<?> allReview(@RequestParam int page,
                                       @RequestParam int size) {
        Page<ReviewBoard> reviewBoardPage = reviewBoardService.findReviewBoards(page - 1, size);
        List<ReviewBoard> reviewBoards = reviewBoardPage.getContent();
        return new ResponseEntity<>(reviewBoards, HttpStatus.OK);
    }

    @GetMapping("/city")
    public HttpEntity<?> cityReview(@RequestParam String city,
                                    @RequestParam int page,
                                    @RequestParam int size) {
        Page<ReviewBoard> reviewBoardPage = reviewBoardService.findCityReviewBoards(page - 1, size, city);
        List<ReviewBoard> reviewBoards = reviewBoardPage.getContent();
        return new ResponseEntity<>(reviewBoards, HttpStatus.OK);

    }

    @GetMapping("/mountain")
    public HttpEntity<?> mountainReview(@RequestParam String mountain,
                                        @RequestParam int page,
                                        @RequestParam int size) {
        Page<ReviewBoard> reviewBoardPage = reviewBoardService.findMountainReviewBoards(page - 1, size, mountain);
        List<ReviewBoard> reviewBoards = reviewBoardPage.getContent();
        return new ResponseEntity<>(reviewBoards, HttpStatus.OK);

    }


    @GetMapping("/course")
    public HttpEntity<?> courseReview(@RequestParam String course,
                                      @RequestParam int page,
                                      @RequestParam int size) {
        Page<ReviewBoard> reviewBoardPage = reviewBoardService.findCourseReviewBoards(page - 1, size, course);
        List<ReviewBoard> reviewBoards = reviewBoardPage.getContent();
        return new ResponseEntity<>(reviewBoards, HttpStatus.OK);

    }

    @GetMapping("/search")
    public HttpEntity<?> courseReview(@RequestParam(required = false) String city,
                                      @RequestParam(required = false) String mountain,
                                      @RequestParam(required = false) String course,
                                      @RequestParam int page,
                                      @RequestParam int size) {

        Map<String, Object> spec = new HashMap<>();
        if (city != null)
            spec.put("localName" , city);
        if (mountain != null)
            spec.put("mountainName", mountain);
        if (course != null)
            spec.put("courseName", course);

        Specification<ReviewBoard> search = ReviewBoardSpecification.search(spec);
        List<ReviewBoard> all = reviewBoardRepository.findAll(search);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }


}
