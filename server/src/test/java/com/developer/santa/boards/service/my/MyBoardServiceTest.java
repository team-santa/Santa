package com.developer.santa.boards.service.my;

import com.developer.santa.boards.entity.ReviewBoard;
import com.developer.santa.boards.repository.ReviewBoardRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MyBoardServiceTest {

    @InjectMocks
    private MyBoardService myBoardService;

    @Mock
    private ReviewBoardRepository reviewBoardRepository;


    @Test
    @DisplayName("createMyBoard 저장기능 테스트")
    void createMyBoard() {
        //given
        ReviewBoard reviewBoard = new ReviewBoard(1L, "서울시", "관악산", "학바위능선", "리뷰제목");
        reviewBoard.setPhoto("");
        reviewBoard.setBody("");
        given(reviewBoardRepository.save(Mockito.any(ReviewBoard.class))).willReturn(reviewBoard);

        //when
        ReviewBoard saveBoard = myBoardService.createMyBoard(reviewBoard);

        //then
        assertThat(saveBoard.getReviewBoardId()).isEqualTo(reviewBoard.getReviewBoardId());
    }

    @Test
    @DisplayName("모두 조회 기능 테스트")

    void findAllMyBoard() {
        //given
        List<ReviewBoard> listBoards = new ArrayList<>();
        ReviewBoard reviewBoard1 = new ReviewBoard(1L, "서울시", "관악산", "학바위능선", "리뷰제목1");
        reviewBoard1.setPhoto("");
        reviewBoard1.setBody("");
        ReviewBoard reviewBoard2 = new ReviewBoard(2L, "서울시", "청계산", "옥녀봉", "리뷰제목2");
        reviewBoard2.setPhoto("");
        reviewBoard2.setBody("");

        listBoards.add(reviewBoard1);
        listBoards.add(reviewBoard2);

        given(reviewBoardRepository.findAll()).willReturn(listBoards);

        //when
        List<ReviewBoard> findAllReview = myBoardService.findAllMyBoard();

        //then
        assertThat(findAllReview.size()).isEqualTo(listBoards.size());
    }

    @Test
    @DisplayName(" 조회 기능 테스트")
    void selectMyBoard() {

        //given
        ReviewBoard reviewBoard = new ReviewBoard(1L, "서울시", "관악산", "학바위능선", "리뷰제목");
        reviewBoard.setPhoto("");
        reviewBoard.setBody("");

        given(reviewBoardRepository.findById(Mockito.anyLong())).willReturn(Optional.of(reviewBoard));  // 조회시, 없을 수도 있기에
        //when
        ReviewBoard findReview = myBoardService.selectMyBoard(1L);

        //then
        assertThat(findReview).isEqualTo(reviewBoard);

    }

    @Test
    @DisplayName(" 업데이트기능 테스트")
    void updateMyBoard() {
        //given
        ReviewBoard reviewBoard = new ReviewBoard(1L, "서울시", "관악산", "학바위능선", "리뷰제목");
        reviewBoard.setPhoto("");
        reviewBoard.setBody("");

        ReviewBoard editBoard = new ReviewBoard(1L, "서울시", "북한산", "백운대코스", "허니잼리뷰");
        reviewBoard.setPhoto("");
        reviewBoard.setBody("");

        given(reviewBoardRepository.findById(Mockito.anyLong())).willReturn(Optional.of(reviewBoard));
        given(reviewBoardRepository.save(Mockito.any(ReviewBoard.class))).willReturn(editBoard);

        //when
        ReviewBoard updateBoard = myBoardService.updateMyBoard(1L, editBoard);
        //then
        assertThat(updateBoard).isEqualTo(editBoard);
    }

    @Test
    void deleteMyBoard() {
        //given
        Long boardId = 1L;
        ReviewBoard reviewBoard = new ReviewBoard(1L, "서울시", "관악산", "학바위능선", "리뷰제목");
        reviewBoard.setPhoto("");
        reviewBoard.setBody("");
        given(reviewBoardRepository.findById(Mockito.anyLong())).willReturn(Optional.of(reviewBoard));

        //when
        myBoardService.deleteMyBoard(boardId);
        //then
        System.out.println("오류안뜨면 성공");
    }
}