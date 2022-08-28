package com.developer.santa.boards.specification;

import com.developer.santa.boards.entity.ReviewBoard;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ReviewBoardSpecification{
    public static Specification<ReviewBoard> equalLocalName(String localName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("localName"), localName));
    }

    public static Specification<ReviewBoard> equalMountainName(String mountainName) {
        return (root, query, builder) -> builder.equal(root.get("mountainName"), mountainName);
    }

    public static Specification<ReviewBoard> equalCourseName(String courseName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("courseName"), courseName);
    }

}
