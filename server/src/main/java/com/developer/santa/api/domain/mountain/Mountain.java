package com.developer.santa.api.domain.mountain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Mountain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String mountainName;

//    //local
//    @ManyToOne
//
//    //course
//    @OneToMany

    @Builder
    public Mountain(String MountainName){
        this.mountainName = MountainName;
    }

}
