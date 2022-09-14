package com.developer.santa.reviewboards.comment.repository;

import com.developer.santa.reviewboards.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
