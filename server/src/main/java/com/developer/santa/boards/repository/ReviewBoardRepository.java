package com.developer.santa.boards.repository;


import com.developer.santa.boards.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard,Long> {

}
