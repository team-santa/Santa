package com.developer.santa.boards.specification;

import com.developer.santa.boards.entity.ReviewBoard;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewBoardSpecification{


    public static Specification<ReviewBoard> equalLocalName(String localName) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("localName").as(String.class), localName));
    }

    public static Specification<ReviewBoard> equalMountainName(String mountainName) {
        return (root, query, builder) -> builder.equal(root.get("mountainName").as(String.class), mountainName);
    }

    public static Specification<ReviewBoard> equalCourseName(String courseName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("courseName").as(String.class), courseName);
    }

    public static Specification<ReviewBoard> search(Map<String, Object> filter){
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filter.forEach((key, value) ->{
                String likeValue = (String)value;
                switch (key){
                    case "localName":
                        Predicate localPredicate = cb.equal(root.get(key).as(String.class), likeValue);
                        predicates.add(localPredicate);
                        break;
                    case "mountainName":
                        Predicate mountainPredicate = cb.equal(root.get(key).as(String.class), likeValue);
                        predicates.add(mountainPredicate);
                        break;
                    case "courseName":
                        Predicate coursePredicate = cb.equal(root.get(key).as(String.class), likeValue);
                        predicates.add(coursePredicate);
                        break;
                }
            });
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }



}
