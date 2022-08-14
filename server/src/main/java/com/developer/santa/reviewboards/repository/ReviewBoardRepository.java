package com.developer.santa.reviewboards.repository;


import com.developer.santa.reviewboards.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewBoardRepository extends JpaRepository<ReviewBoard,Long> {
//    List<Optional<ReviewBoard>> findAllByLocal(String local);
//    List<Optional<ReviewBoard>> findAllByMountain(String mountain);
//    List<Optional<ReviewBoard>> findAllByCourse(String course);
}
