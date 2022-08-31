package com.developer.santa.tag.controller;

import com.developer.santa.tag.entity.Tag;
import com.developer.santa.tag.mapper.TagMapper;
import com.developer.santa.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    private final TagMapper mapper;
    @GetMapping
    public ResponseEntity<?> tagSearch(@RequestParam String text){
        Map<String, Object> spec = new HashMap<>();
        if(text != null){
            spec.put("tagName" , text);
        }

        Page<Tag> tagPage = tagService.findTags(0, spec);
        List<Tag> tags = tagPage.getContent();

        return new ResponseEntity<>(mapper.tagListToRecommendList(tags), HttpStatus.OK);


    }
}
