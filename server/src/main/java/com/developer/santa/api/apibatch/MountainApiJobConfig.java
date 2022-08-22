package com.developer.santa.api.apibatch;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MountainApiJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;


    @Bean
    public MountainApiReader reader() {
        return new MountainApiReader();
    }

    @Bean
    public MountainApiProcessor processor(){
        return new MountainApiProcessor();
    }

    @Bean
    public MountainApiWriter<String> writer() {
        JpaItemWriter<String> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return new MountainApiWriter<>(writer);
    }

    @Bean
    public Job MountainApiJob() {
        return jobBuilderFactory.get("MountainApiJob")
                .start(MountainApiStep1())
                .build();
    }

    @Bean
    public Step MountainApiStep1() {
        return stepBuilderFactory.get("MountainApiStep1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>>> mountainApiStep1");
                    return RepeatStatus.FINISHED;
                }).build();
    }

//    @Bean
//    public Step MountainApiStep2(){
//        return stepBuilderFactory.get("MountainApiStep2")
//                .<String, String> chunk(10)
//                .reader(reader())
//                .build();
//
//    }


}
