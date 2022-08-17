package com.developer.santa.boards.review.repository;

import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.repository.ReviewBoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
class ReviewBoardRepositoryTest {

    @Autowired
    public ReviewBoardRepository reviewBoardRepository;

    @Test
    @DisplayName("ReviewBoard DB 저장 테스트")
    void saveReview() {
        //given
        ReviewBoard reviewBoard = new ReviewBoard(1L, "서울시", "관악산", "학바위능선", "리뷰제목");
        // 내용과 사진은 생략

        //when
        ReviewBoard saveBoard = reviewBoardRepository.save(reviewBoard);

        //then
        Assertions.assertThat(saveBoard.getId()).isEqualTo(reviewBoard.getId());

    }

    @Test
    @DisplayName("저장된 ReviewBoard 조회Test")
    void findReview() {
        //given
        ReviewBoard saveBoard1 = reviewBoardRepository.save(new ReviewBoard(1L, "서울시", "관악산", "학바위능선", "리뷰제목1"));
        ReviewBoard saveBoard2 = reviewBoardRepository.save(new ReviewBoard(2L, "서울시", "관악산", "학바위능선", "리뷰제목2"));
        //when

        ReviewBoard findBoard1= reviewBoardRepository.findById(saveBoard1.getId())
                .orElseThrow(()-> new IllegalArgumentException("Board ID : "+ saveBoard1.getId() + " 조회할수 없습니다."));
        ReviewBoard findBoard2= reviewBoardRepository.findById(saveBoard2.getId())
                .orElseThrow(()-> new IllegalArgumentException("Board ID : "+ saveBoard2.getId() + " 조회할수 없습니다."));

        //then
        Assertions.assertThat(saveBoard1.getId()).isEqualTo(findBoard1.getId());
        Assertions.assertThat(saveBoard2.getId()).isEqualTo(findBoard2.getId());

    }


}