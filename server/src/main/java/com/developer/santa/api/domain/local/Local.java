package com.developer.santa.api.domain.local;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Local {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "local_id")
    private Long id;

    @Column
    private String localName;

    @Builder
    public Local(String localName){
        this.localName = localName;
    }
}
