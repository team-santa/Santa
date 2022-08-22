package com.developer.santa.api.domain.local;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Local {
    @Id
    private int id;

    @Column
    private String localName;

//    //mountain
//    @OneToMany
}
