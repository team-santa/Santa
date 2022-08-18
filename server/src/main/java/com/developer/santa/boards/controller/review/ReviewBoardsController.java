package com.developer.santa.boards.controller.review;

import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review/boards")
public class ReviewBoardsController {

    @GetMapping
    public HttpEntity<?> allReview(){
        return null;
    }

    @GetMapping("/{cityId}")
    public HttpEntity<?> cityReview(){
        return null;
    } //조회 기능

    @GetMapping("/{cityId}/{mountainId}")
    public HttpEntity<?> mountainReview(){
        return null;
    }

    @GetMapping("/{cityId}/{mountainId}/{courseId}")
    public HttpEntity<?> courseReview(){
        return null;
    }

    // 최신순
    // 조회순

}
