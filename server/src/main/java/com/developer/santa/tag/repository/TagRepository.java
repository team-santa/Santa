package com.developer.santa.tag.repository;

import com.developer.santa.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TagRepository extends JpaRepository<Tag, Long> , JpaSpecificationExecutor<Tag> {
}
