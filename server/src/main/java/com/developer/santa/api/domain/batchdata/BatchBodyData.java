package com.developer.santa.api.domain.batchdata;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class BatchBodyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String batchDataBody;

    @Builder
    public BatchBodyData(String batchDataBody){
        this.batchDataBody=batchDataBody;
    }
}
