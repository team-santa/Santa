package com.developer.santa.api.domain.local;

import com.developer.santa.api.domain.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalRepository extends JpaRepository<Local, Long> {
    Boolean existsByLocalName(String localName);
    Local findByLocalName(String localName);
}
