package com.developer.santa.boards.service.review;

import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.repository.ReviewBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class ReviewBoardService {
    @Autowired
    public ReviewBoardRepository reviewBoardRepository;


    public Page<ReviewBoard> findReviewBoards(int page, int size){
        return reviewBoardRepository.findAll(PageRequest.of(page, size,
                Sort.by("reviewBoardId").descending()));
    }

}
