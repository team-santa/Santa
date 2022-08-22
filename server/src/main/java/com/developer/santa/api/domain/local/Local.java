package com.developer.santa.api.domain.local;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Local {
    @Id
    private int id;

    @Column
    private String localName;

//    //mountain
//    @OneToMany
}
