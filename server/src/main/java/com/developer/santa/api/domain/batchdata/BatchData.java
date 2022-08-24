package com.developer.santa.api.domain.batchdata;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BatchData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String reqUrl;

    @Builder
    public BatchData(String reqUrl){
        this.reqUrl=reqUrl;
    }
}
