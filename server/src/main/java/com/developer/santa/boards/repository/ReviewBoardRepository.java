package com.developer.santa.boards.repository;


import com.developer.santa.boards.entity.ReviewBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ReviewBoardRepository extends JpaRepository<ReviewBoard,Long> {
    Page<ReviewBoard> findByLocalName(String localName, PageRequest pageRequest);
    Page<ReviewBoard> findByMountainName(String mountainName, PageRequest pageRequest);
    Page<ReviewBoard> findByCourseName(String courseName, PageRequest pageRequest);

    Page<ReviewBoard> findByLocalNameAndMountainNameAndCourseName(String localName,String mountainName,String courseName, PageRequest pageRequest);

}
