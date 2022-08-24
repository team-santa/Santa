package com.developer.santa.boards.service.my;

import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.repository.ReviewBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class MyBoardService {
    @Autowired
    private ReviewBoardRepository reviewBoardRepository;


    // 게시판 저장
    // 유효성 검사필요!
    public ReviewBoard createMyBoard(ReviewBoard reviewBoard){
        return reviewBoardRepository.save(reviewBoard);
    }

    //게시판 업데이트
    //예외처리필요
    public ReviewBoard updateMyBoard(Long id, ReviewBoard editReviewBoard){
        ReviewBoard updateBoard = findVerifiedReviewBoard(id);

        Optional.ofNullable(editReviewBoard.getTitle()).ifPresent(updateBoard::setTitle);
        Optional.ofNullable(editReviewBoard.getLocalName()).ifPresent(updateBoard::setLocalName);
        Optional.ofNullable(editReviewBoard.getMountainName()).ifPresent(updateBoard::setMountainName);
        Optional.ofNullable(editReviewBoard.getCourseName()).ifPresent(updateBoard::setCourseName);
        Optional.ofNullable(editReviewBoard.getBody()).ifPresent(updateBoard::setBody);
        Optional.ofNullable(editReviewBoard.getPhoto()).ifPresent(updateBoard::setPhoto);

        return reviewBoardRepository.save(updateBoard);
    }


    // 하나의 게시판 조회
    public ReviewBoard selectMyBoard(Long id){
        return findVerifiedReviewBoard(id);
    }

    // 모든 게시판 조회
    public List<ReviewBoard> findAllMyBoard(){
        return reviewBoardRepository.findAll();
    }



    //게시판 삭제
    public void deleteMyBoard(Long id){
        ReviewBoard findBoard = reviewBoardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지않는 게시판입니다. 게시판번호 : " + id)
        );
        reviewBoardRepository.delete(findBoard);
    }


    //게시판 id가 있는지 확인 메소드
    public ReviewBoard findVerifiedReviewBoard(Long id){
        Optional<ReviewBoard> optionalReviewBoard = reviewBoardRepository.findById(id);
        return optionalReviewBoard.orElseThrow(
                () -> new IllegalArgumentException("존재하지않는 게시판입니다. 게시판번호 : " + id)
        );
    }
}
