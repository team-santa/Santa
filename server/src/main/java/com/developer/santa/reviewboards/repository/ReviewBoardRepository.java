package com.developer.santa.reviewboards.repository;


import com.developer.santa.reviewboards.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ReviewBoardRepository extends JpaRepository<ReviewBoard,Long> , JpaSpecificationExecutor<ReviewBoard> {

}
