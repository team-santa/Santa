package com.developer.santa.tag.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

import java.util.List;


@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @Column(length = 100, nullable = false)
    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<TagSelect> tagSelects;


    public void addTagSelect(TagSelect tagSelect){
        this.tagSelects.add(tagSelect);
        if(tagSelect.getTag() != this){
            tagSelect.addTag(this);
        }
    }
}



