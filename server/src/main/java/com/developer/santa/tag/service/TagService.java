package com.developer.santa.tag.service;

import com.developer.santa.tag.entity.Tag;
import com.developer.santa.tag.repository.TagRepository;
import com.developer.santa.tag.specification.TagSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Page<Tag> findTags(int page, Map<String, Object> spec){
        Specification<Tag> search = TagSpecification.search(spec);
        return tagRepository.findAll(search, PageRequest.of(page,10));
    }
}
