package com.developer.santa.reviewboards.repository;


import com.developer.santa.reviewboards.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ReviewBoardRepository extends JpaRepository<ReviewBoard,Long> , JpaSpecificationExecutor<ReviewBoard> {
    @Transactional
    @Modifying
    @Query("UPDATE ReviewBoard r set r.views = r.views + 1 where r.reviewBoardId = :id")
    int updateView(@Param("id")Long id);
//    @Modifying
//    @Query(value = "insert into review_board_viewers (REVIEW_BOARD_REVIEW_BOARD_ID, viewers) VALUES  (:id ,:clientIp) ", nativeQuery = true)
//    void insertIntoViewer(@Param("id")Long id ,@Param("clientIp")String clientIp);

}
