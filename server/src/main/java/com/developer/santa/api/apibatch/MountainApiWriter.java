package com.developer.santa.api.apibatch;


import lombok.AllArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class MountainApiWriter<T> extends JpaItemWriter<List<T>> {
    private final JpaItemWriter<T> jpaItemWriter;

    @Override
    @Transactional
    public void write(List<? extends List<T>> items) {

        List<T> collect = new ArrayList<>();

        for(List<T> list : items){
            collect.addAll(list);
        }

        jpaItemWriter.write(collect);
    }

    @Override
    public void afterPropertiesSet(){

    }
}