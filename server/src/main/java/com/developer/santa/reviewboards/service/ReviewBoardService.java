package com.developer.santa.reviewboards.service;

import com.developer.santa.reviewboards.entity.ReviewBoard;
import com.developer.santa.reviewboards.repository.ReviewBoardRepository;
import com.developer.santa.reviewboards.specification.ReviewBoardSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewBoardService {

    public final ReviewBoardRepository reviewBoardRepository;

    public ReviewBoard createMyBoard(ReviewBoard reviewBoard){
        return reviewBoardRepository.save(reviewBoard);
    }

    public ReviewBoard updateMyBoard(Long id, ReviewBoard editReviewBoard) {
        ReviewBoard updateBoard = findVerifiedReviewBoard(id);

        Optional.ofNullable(editReviewBoard.getTitle()).ifPresent(updateBoard::setTitle);
        Optional.ofNullable(editReviewBoard.getLocalName()).ifPresent(updateBoard::setLocalName);
        Optional.ofNullable(editReviewBoard.getMountainName()).ifPresent(updateBoard::setMountainName);
        Optional.ofNullable(editReviewBoard.getCourseName()).ifPresent(updateBoard::setCourseName);
        Optional.ofNullable(editReviewBoard.getBody()).ifPresent(updateBoard::setBody);
        Optional.ofNullable(editReviewBoard.getPhoto()).ifPresent(updateBoard::setPhoto);

        return reviewBoardRepository.save(updateBoard);

    }

    public Page<ReviewBoard> findReviewBoards(int page, Map<String, Object> spec) {
        Specification<ReviewBoard> search = ReviewBoardSpecification.search(spec);
        return reviewBoardRepository.findAll(search, PageRequest.of(page, 10, Sort.by("reviewBoardId").descending()));
    }

    public ReviewBoard findReviewBoard(Long reviewBoardId, String clientIp) {

        ReviewBoard reviewBoard = findVerifiedReviewBoard(reviewBoardId);
        if(!reviewBoard.getViewers().contains(clientIp)) {
            reviewBoard.addViewCount();
            reviewBoard.addViewer(clientIp);
        }
        return findVerifiedReviewBoard(reviewBoardId);
    }


    public void deleteReviewBoard(Long reviewBoardId) {
        ReviewBoard findReviewBoard = findVerifiedReviewBoard(reviewBoardId);
        reviewBoardRepository.delete(findReviewBoard);
    }


    public ReviewBoard findVerifiedReviewBoard(Long reviewBoardId) {
        Optional<ReviewBoard> optionalReviewBoard = reviewBoardRepository.findById(reviewBoardId);
        return optionalReviewBoard.orElseThrow(
                () -> new IllegalArgumentException("존재하지않는 게시판입니다. 게시판번호 :" + reviewBoardId)
        );
    }

}


