package com.developer.santa.boards.repository;


import com.developer.santa.boards.entity.ReviewBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface ReviewBoardRepository extends JpaRepository<ReviewBoard,Long> , JpaSpecificationExecutor<ReviewBoard> {

}
