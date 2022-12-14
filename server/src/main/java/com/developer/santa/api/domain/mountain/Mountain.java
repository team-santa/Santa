package com.developer.santa.api.domain.mountain;

import com.developer.santa.api.domain.local.Local;
import com.developer.santa.member.entity.FavoriteMountain;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Mountain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mountain_id")
    private Long id;

    @Column
    private String mountainName;

    //local
    @ManyToOne()
    @JoinColumn(name = "local_id")
    private Local local;

    @Builder
    public Mountain(String mountainName, Local local){
        this.mountainName = mountainName;
        this.local = local;
    }

}
