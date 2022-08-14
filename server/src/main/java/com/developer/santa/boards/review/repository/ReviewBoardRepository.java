package com.developer.santa.boards.review.repository;


import com.developer.santa.boards.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard,Long> {
//    List<Optional<ReviewBoard>> findAllByLocal(String local);
//    List<Optional<ReviewBoard>> findAllByMountain(String mountain);
//    List<Optional<ReviewBoard>> findAllByCourse(String course);
}
