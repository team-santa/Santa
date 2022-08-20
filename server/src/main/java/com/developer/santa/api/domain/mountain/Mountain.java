package com.developer.santa.api.domain.mountain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Mountain {

    @Id
    private int id;

    @Column
    private String mountainName;

//    //local
//    @ManyToOne
//
//    //load
//    @OneToMany
}
