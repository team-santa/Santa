package com.developer.santa.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long tagId;

    @Id
    @Column(length = 100, nullable = false, unique = true)
    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<TagSelect> tagSelects = new ArrayList<>();

    public Tag(String tagName) {
        this.tagName = tagName;
    }

//    public Tag(String tagName) {
//        this.tagName = tagName;
//    }

    public void addTagSelect(TagSelect tagSelect){
        this.tagSelects.add(tagSelect);
        if(tagSelect.getTag() != this){
            tagSelect.addTag(this);
        }
    }

}



