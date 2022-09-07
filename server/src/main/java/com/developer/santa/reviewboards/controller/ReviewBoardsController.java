package com.developer.santa.reviewboards.controller;

import com.developer.santa.member.service.MemberService;
import com.developer.santa.reviewboards.dto.ReviewBoardRequestDto;
import com.developer.santa.reviewboards.entity.ReviewBoard;
import com.developer.santa.reviewboards.mapper.ReviewBoardMapper;
import com.developer.santa.reviewboards.service.ReviewBoardService;
import com.developer.santa.dto.MultiResponseDto;
import com.developer.santa.tag.entity.Tag;
import com.developer.santa.tag.entity.TagSelect;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/reviewboards")
@RequiredArgsConstructor
@CrossOrigin(origins="*", allowedHeaders = "*")
public class ReviewBoardsController {
    // 최신순조회
    private final ReviewBoardService reviewBoardService;
    private final ReviewBoardMapper mapper;

    @PostMapping
    public ResponseEntity<?> postReview(@Valid @RequestBody ReviewBoardRequestDto.Post requestBody){
        ReviewBoard reviewBoard = mapper.reviewBoardPostToReviewBoard(requestBody);
        ReviewBoard createBoard = reviewBoardService.createMyBoard(reviewBoard);
        return new ResponseEntity<>(createBoard, HttpStatus.CREATED);
    }



    @GetMapping
    public HttpEntity<?> courseReview(@RequestParam(required = false) String city,
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


    @GetMapping("/{reviewBoardId}")
    public ResponseEntity<?> findReviewBoard(@PathVariable Long reviewBoardId,
                                                       HttpServletRequest request) {

        String clientIp = getClientIp(request);
        ReviewBoard selectMyBoard = reviewBoardService.findReviewBoard(reviewBoardId, clientIp);

        return new ResponseEntity<>(mapper.reviewBoardToDetail(selectMyBoard), HttpStatus.OK);
    }

    @PatchMapping("/{reviewBoardId}")
    public ResponseEntity<?> editReviewBoard(@PathVariable Long reviewBoardId,
                                             @Valid @RequestBody ReviewBoardRequestDto.Patch requestBody) {
        ReviewBoard reviewBoard = mapper.reviewBoardPatchToReviewBoard(requestBody);
        ReviewBoard updateBoard = reviewBoardService.updateMyBoard(reviewBoardId, reviewBoard);
        return new ResponseEntity<>(mapper.reviewBoardToDetail(updateBoard), HttpStatus.OK);
    }

    @DeleteMapping("/{reviewBoardId}")
    public ResponseEntity<?> deleteReviewBoard(@PathVariable Long reviewBoardId) {
        reviewBoardService.deleteReviewBoard(reviewBoardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public static String getClientIp(HttpServletRequest request){
        String clientIp = null;
        boolean isIpInHeader = false;

        List<String> headerList = new ArrayList<>();
        headerList.add("X-Forwarded-For");
        headerList.add("HTTP_CLIENT_IP");
        headerList.add("HTTP_X_FORWARDED_FOR");
        headerList.add("HTTP_X_FORWARDED");
        headerList.add("HTTP_FORWARDED_FOR");
        headerList.add("HTTP_FORWARDED");
        headerList.add("Proxy-Client-IP");
        headerList.add("WL-Proxy-Client-IP");
        headerList.add("HTTP_VIA");
        headerList.add("IPV6_ADR");

        for (String header: headerList) {
            clientIp = request.getHeader(header);
            if(StringUtils.hasText(clientIp) && !clientIp.equals("unknown")){
                isIpInHeader = true;
                break;
            }
        }
        if (!isIpInHeader){
            clientIp = request.getRemoteAddr();
        }
        return  clientIp;
    }
}
