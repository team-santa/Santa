package com.developer.santa.boards.service.review;

import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.repository.ReviewBoardRepository;
import com.developer.santa.boards.specification.ReviewBoardSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.print.Pageable;
import java.util.Map;

@Service
public class ReviewBoardService {
    @Autowired
    public ReviewBoardRepository reviewBoardRepository;

    public Page<ReviewBoard> findReviewBoards(int page, Map<String, Object> spec){
        Specification<ReviewBoard> search = ReviewBoardSpecification.search(spec);
        return reviewBoardRepository.findAll(search,PageRequest.of(page,10, Sort.by("reviewBoardId").descending()));
    }


}
