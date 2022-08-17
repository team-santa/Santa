package com.developer.santa.boards.service.my;

import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.repository.ReviewBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class MyBoardService {
    @Autowired
    private ReviewBoardRepository reviewBoardRepository;


    // 게시판 저장
    // 유효성 검사필요!
    public ReviewBoard createMyBoard(ReviewBoard reviewBoard){
        return reviewBoardRepository.save(reviewBoard);
    }
    // 모든 게시판 조회
    public List<ReviewBoard> findAllMyBoard(){
        return reviewBoardRepository.findAll();
    }

    // 하나의 게시판 조회
    public ReviewBoard selectMyBoard(Long id){
        return reviewBoardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지않는 게시판입니다. 게시판번호 : " + id)
        );
    }

    //게시판 업데이트
    //예외처리필요
    public ReviewBoard updateMyBoard(Long id, ReviewBoard editReviewBoard){
        ReviewBoard updateBoard = reviewBoardRepository.findById(id).orElseThrow(
            ()-> new IllegalArgumentException("존재하지않는 게시판입니다. 게시판번호 : " + id)
        );
        updateBoard.setTitle(editReviewBoard.getTitle());
        updateBoard.setLocalName(editReviewBoard.getLocalName());
        updateBoard.setMountainName(editReviewBoard.getMountainName());
        updateBoard.setCourseName(editReviewBoard.getCourseName());
        updateBoard.setBody(editReviewBoard.getBody());
        updateBoard.setPhoto(editReviewBoard.getPhoto());

        return reviewBoardRepository.save(updateBoard);
    }

    //게시판 삭제
    public void deleteMyBoard(Long id){
        ReviewBoard findBoard = reviewBoardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지않는 게시판입니다. 게시판번호 : " + id)
        );
        reviewBoardRepository.delete(findBoard);
    }
}
