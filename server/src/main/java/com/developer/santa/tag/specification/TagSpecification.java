package com.developer.santa.tag.specification;

import com.developer.santa.tag.entity.Tag;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TagSpecification {

    public static Specification<Tag> search(Map<String, Object> filter){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            filter.forEach((key, value)-> {
                String likeValue = "%"+ value + "%";
                switch (key){
                    case "tagName":
                        Predicate textPredicate = criteriaBuilder.like(root.get(key).as(String.class), likeValue);
                        predicates.add(textPredicate);
                        break;
                }
            });
            return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
