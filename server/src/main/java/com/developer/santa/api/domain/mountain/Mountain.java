package com.developer.santa.api.domain.mountain;

import com.developer.santa.api.domain.local.Local;
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
    @Column(name = "mountain_id")
    private Long id;

    @Column
    private String mountainName;

    //local
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "local_id")
    private Local local;

    @Builder
    public Mountain(String MountainName, Local local){
        this.mountainName = MountainName;
        this.local = local;
    }



}
